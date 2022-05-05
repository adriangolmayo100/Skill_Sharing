package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.dao.StudentDao;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tipos_usuario")
public class UserController {
    private StudentDao studentDao;

    @Autowired
    public void setDao(StudentDao userDao){ this.studentDao = userDao; }

    @RequestMapping("/list")
    public String pagUser(HttpSession session, Model model){
        if (session.getAttribute("user") == null){
            model.addAttribute("user", new Student());
            return "login";
        }else{
            Student usuario = (Student) session.getAttribute("user");
            String nomUser = usuario.getUsername();
            String password = usuario.getPassword();
        }
        model.addAttribute("users", studentDao.obtenerTodosStudent());
        return session.getAttribute("nextUrl").toString();
    }

    @RequestMapping("/usuario")
    public String prueba(Model model){
        return "tipos_usuario/usuario";
    }
}
