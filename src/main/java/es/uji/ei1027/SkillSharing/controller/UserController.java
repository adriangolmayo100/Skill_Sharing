package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.SkillSharing.dao.UserInt;
import es.uji.ei1027.SkillSharing.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserInt userInt;

    @Autowired
    public void setUserDao(UserInt userInt){ this.userInt = userInt; }

    @RequestMapping("/list")
    public String pagUser(HttpSession session, Model model){
        if (session.getAttribute("user") == null){
            model.addAttribute("user", new User());
            return "login";
        }else{
            User usuario = (User) session.getAttribute("user");
            String nomUser = usuario.getUsername();
            String password = usuario.getPassword();
        }
        return "vacio";
    }
}
