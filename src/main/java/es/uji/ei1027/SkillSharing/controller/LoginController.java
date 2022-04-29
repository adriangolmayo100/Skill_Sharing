package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.dao.UserDao;
import es.uji.ei1027.SkillSharing.dao.UserInt;
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

import java.io.FileNotFoundException;

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User("", ""));
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)          //Comprobación de login
    public String checkLogin(@ModelAttribute("user") User user,
                             BindingResult bindingResult, HttpSession session) throws FileNotFoundException {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        String nomUser = user.getUsername();
        String password = user.getPassword();
        user = userDao.cargaUsuario(nomUser, password);
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya o usuari incorrecte");
            return "login";
        }
        session.setAttribute("user", user);

        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        if (session.getAttribute("nextUrl") != null)
            return "redirect:" + session.getAttribute("nextUrl").toString();

        // Lleva a la página de usuario
        return "usuario";
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
        return User.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User usuario = (User) target;
        if (usuario.getUsername().equals(""))
            errors.rejectValue("username", "badUser", "user required");
        if (usuario.getPassword().equals("")) {
            errors.rejectValue("password", "badPasswd", "password required");
        }
    }
}
