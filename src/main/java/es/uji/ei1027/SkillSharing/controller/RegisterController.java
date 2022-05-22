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

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/register")
public class RegisterController {
    StudentDao studentDao = new StudentDao();


    @Autowired
    public void setRequestDao(StudentDao studentDao) {
        this.studentDao=studentDao;
    }

    @RequestMapping("/datos")
    public String registerDatos(HttpSession session, Model model){
        model.addAttribute("student", new Student());
        return "register/datos";
    }

    @RequestMapping(value="/datos", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("student") Student student,
                                   BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return "register/datos";
        String user = student.getUsername();
        if(studentDao.obtenerStudentConUser(user) != null){ //Si ya existe usuario con esta cuenta, no vamos a registrarlo
            return "register/usuarioExistente";
        }
        RegisterValidator registerValidator = new RegisterValidator();
        registerValidator.validate(student, bindingResult);
        if (bindingResult.hasErrors()){
            return "register/datos";
        }
        int max_id = studentDao.getNextId();
        if (max_id == -1)
            max_id = 0;
        student.setIdStudent(max_id);
        student.setSkp(false);
        student.setHoursGiven(0);
        student.setHoursReceived(0);
        student.setUnavailable(false);
        student.setBanReason("");
        studentDao.nuevoStudent(student);
        return "feedback/skp_correcto";
    }

}
