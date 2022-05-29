package es.uji.ei1027.SkillSharing.controller;

import javax.servlet.http.HttpSession;

import es.uji.ei1027.SkillSharing.dao.StatisticDao;
import es.uji.ei1027.SkillSharing.dao.StudentDao;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tipos_usuario")
public class UserController {
    private UserValidator validator = new UserValidator();

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StatisticDao statisticDao;

    @RequestMapping("/pagUser")
    public String pagUser(HttpSession session, Model model){
        if (session.getAttribute("user") == null){
            model.addAttribute("user", new Student());
            return "login";
        }else{
            Student usuario = (Student) session.getAttribute("user");
            String nomUser = usuario.getUsername();
            String password = usuario.getPassword();
        }
        return session.getAttribute("nextUrl").toString();
    }

    @RequestMapping("/usuario")
    public String pagStudent(HttpSession session, Model model){
        String mensaje = validator.comprobar_conexion(session, model, "/tipos_usuario/usuario", true);
        if (!mensaje.equals(""))
            return mensaje;
        Student student = (Student) session.getAttribute("student");
        if (student.isUnavailable()){
            model.addAttribute("banReason",student.getBanReason());
            return "tipos_usuario/baneado";
        }
        if (student.isSkp())
            return "tipos_usuario/skp";
        return "tipos_usuario/usuario";
    }
   @RequestMapping("/visitante")
    public String pagVisitante(HttpSession session, Model model){
       Student student1 = (Student) session.getAttribute("student");
       if ( student1 != null)
       {
           session.invalidate();
       }
        return "tipos_usuario/visitante";
    }
}
