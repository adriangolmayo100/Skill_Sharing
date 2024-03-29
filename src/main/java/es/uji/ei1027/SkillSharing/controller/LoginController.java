package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.dao.StudentDao;
import es.uji.ei1027.SkillSharing.model.Student;
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
    public String login(HttpSession session, Model model){
        Student student= (Student) session.getAttribute("student");
        model.addAttribute("student", new Student());
        if ( student != null)
        {
            return "redirect:/tipos_usuario/usuario";
        }
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)          //Comprobación de login
    public String checkLogin(@ModelAttribute("student") Student student, Model model,
                             BindingResult bindingResult, HttpSession session) throws FileNotFoundException {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(student, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari

        if (studentDao.loadStudent(student.getUsername(),student.getPassword()) == null){
            bindingResult.rejectValue("password", "valor incorrecto", "Usuario o contraseña incorrecta");
            return "login";
        }
        String username = student.getUsername();
        Student student_completo = studentDao.obtenerStudentConUser(username);
        session.setAttribute("student", student_completo);
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        if (session.getAttribute("nextUrl") != null){
            String nextUrl = session.getAttribute("nextUrl").toString();
            session.removeAttribute("nextUrl");
            return "redirect:" + nextUrl;
        }

        // Lleva a la página de usuario
        return "redirect:/tipos_usuario/usuario";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

class UserValidator implements Validator {

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
            errors.rejectValue("password", "badPassword", "contraseña requerida");
    }
    public String comprobar_conexion(HttpSession session, Model model, String url, boolean skp) {
        Student student = (Student) session.getAttribute("student");
        if (url.equals("/tipos_usuario/usuario")) {
            if (student == null) {
                session.setAttribute("nextUrl", url);
                model.addAttribute("student", new Student());
                return "login";
            }
        }else {
            if (student == null) {
                session.setAttribute("nextUrl", url);
                model.addAttribute("student", new Student());
                return "login";
            }
            if (skp != student.isSkp()) {
                session.setAttribute("nextUrl", url);
                model.addAttribute("student", new Student());
                return "feedback/cuenta_incorrecta";
            }
        }
        return "";
    }
}
