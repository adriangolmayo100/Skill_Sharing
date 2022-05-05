package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.StudentDao;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
@RequestMapping("/register")
public class RegisterController {
    UserDao userDao = new UserDao();
    StudentDao studentDao = new StudentDao();

    @Autowired
    public void setRequestDao(StudentDao studentDao, UserDao userDao) {
        this.userDao = userDao;
        this.studentDao=studentDao;
    }

    @RequestMapping("/datos")
    public String registerDatos(Model model){
        model.addAttribute("student", new Student());
    return "register/datos";
    }

    @RequestMapping(value="/datos", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("student") Student student,
                                   BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return "register/datos";
        int cantStudent = studentDao.obtenerTodosStudent().size();
        student.setIdStudent(cantStudent + 1);
        student.setBalance(0);
        studentDao.nuevoStudent(student);
        return "redirect:../";
    }

    @RequestMapping("/user")
    public String registerUser(Model model){
        model.addAttribute("student", new Student());
        return "register/user";
    }
    @RequestMapping(value="/student", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("student") User user,
                                   BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return "register/datos";
        if(! userDao.nuevoUsuario(user.getUsername(), user.getPassword())){
            return "register/usuarioExistente"; //Si ya existe usuario con esta cuenta, no vamos a registrarlo
        }
        return "redirect:datos";
    }

}
