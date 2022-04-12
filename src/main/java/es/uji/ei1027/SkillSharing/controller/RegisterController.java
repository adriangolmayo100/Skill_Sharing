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

@Controller
@RequestMapping("/register")
public class RegisterController {

    @RequestMapping("/datos")
    public String registerUser(Model model){
    /*    model.addAttribute("user", new User());
        return "register";
    }*/return "register/datos";
    }

    @RequestMapping("/user")
    public String registerDatos(Model model){
    /*    model.addAttribute("user", new User());
        return "register";
    }*/return "register/user";
    }

}
