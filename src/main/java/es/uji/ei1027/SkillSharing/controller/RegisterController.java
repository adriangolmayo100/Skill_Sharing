package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.dao.StudentDao;
import es.uji.ei1027.SkillSharing.dao.UserDao;
import es.uji.ei1027.SkillSharing.dao.UserInt;
import es.uji.ei1027.SkillSharing.model.SkillType;
import es.uji.ei1027.SkillSharing.model.Student;
import es.uji.ei1027.SkillSharing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.IOException;

@Controller
@RequestMapping("/register")
public class RegisterController {
    UserDao userDao = new UserDao();
    StudentDao studentDao = new StudentDao();


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
        student.setIdStudent(Integer.parseInt(userDao.obtenerId()));
        student.setBalance(0);
        student.setSkp(false);
        studentDao.nuevoStudent(student);
        return "redirect:datos";
    }

    @RequestMapping("/user")
    public String registerUser(Model model){
        model.addAttribute("user", new User("", ""));
        return "register/user";
    }
    @RequestMapping(value="/user", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("user") User user,
                                   BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return "register/user";
        userDao.nuevoUsuario(user.getUsername(), user.getPassword());
        return "redirect:datos";
    }

}
