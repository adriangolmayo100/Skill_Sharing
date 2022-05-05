package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tipos_usuario")
public class UserController {
    private UserDao userDao;

    @Autowired
    public void setDao(UserDao userDao){ this.userDao = userDao; }

    @RequestMapping("/list")
    public String pagUser(HttpSession session, Model model){
        if (session.getAttribute("user") == null){
            model.addAttribute("user", new User("", ""));
            return "login";
        }else{
            User usuario = (User) session.getAttribute("user");
            String nomUser = usuario.getUsername();
            String password = usuario.getPassword();
        }
        model.addAttribute("users", userDao.listAllUsers());
        return session.getAttribute("nextUrl").toString();
    }

    @RequestMapping("/usuario")
    public String prueba(Model model){
        return "tipos_usuario/usuario";
    }
}
