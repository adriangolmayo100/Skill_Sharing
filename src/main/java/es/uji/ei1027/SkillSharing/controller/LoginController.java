package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.dao.StudentDao;
import es.uji.ei1027.SkillSharing.model.Student;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.FileNotFoundException;

@Controller
public class LoginController {

    @Autowired
    private StudentDao studentDao;

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("student", new Student());
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)          //Comprobación de login
    public String checkLogin(@ModelAttribute("student") Student student,
                             BindingResult bindingResult, HttpSession session) throws FileNotFoundException {
        UserValidator userValidator = new UserValidator();
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        //String nomUser = student.getUsername();
        //String password = student.getPassword();
        userValidator.validate(student, bindingResult);
        /*user = userDao.cargaUsuario(nomUser, password);
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya o usuari incorrecte");
            return "login";
        }*/
        if (studentDao.loadStudent(student.getUsername(),student.getPassword()) == null){
            bindingResult.rejectValue("username", "badUser", "no se ha introducido un usuario");
        }
        session.setAttribute("student", student);
        System.out.println(student.getUsername());
        System.out.println(student.getPassword());
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        if (session.getAttribute("nextUrl") != null)
            return "redirect:" + session.getAttribute("nextUrl").toString();

        // Lleva a la página de usuario
        return "redirect:tipos_usuario/usuario";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

class UserValidator implements Validator {      //Clase para comprobar que no se pasan espacios en blanco. En verdad es una chorrada ya que esto lo hace el HTML

    @Override
    public boolean supports(Class<?> cls) {
        return Student.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Student student = (Student) target;
        String username = student.getUsername();
        String password = student.getPassword();
        if (username.equals(""))
            errors.rejectValue("username", "badUser", "usuario requerido");
        if (password.equals(""))
            errors.rejectValue("password", "badPassword", "usuario requerido");


    }
}
