package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.OfferDao;
import es.uji.ei1027.SkillSharing.dao.RequestDao;
import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import es.uji.ei1027.SkillSharing.dao.StudentDao;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private UserValidator validator = new UserValidator();

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private OfferDao offerDao;
    @Autowired
    private RequestDao requestDao;
    @Autowired
    private SkillTypeDao skillTypeDao;
    @RequestMapping("/list")
    public String listarUsuarios(HttpSession session, Model model){
        String mensaje = validator.comprobar_conexion(session, model, "/student/list", true);
        if (!mensaje.equals(""))
            return mensaje;
        List<Student> studentList = studentDao.getStudents();
        model.addAttribute("studentList",studentList);
        return "student/list";
    }
    @RequestMapping("/informacion/{idStudent}")
    public String informacionUsuario(HttpSession session, Model model, @PathVariable int idStudent){
        String mensaje = validator.comprobar_conexion(session, model, "/student/informacion/"+idStudent, true);
        if (!mensaje.equals(""))
            return mensaje;
        model.addAttribute("student",studentDao.getStudent(idStudent));
        model.addAttribute("students",studentDao.getStudents());
        model.addAttribute("offers",offerDao.getOffers(idStudent));
        model.addAttribute("request",requestDao.getRequests(idStudent));
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());

        return "/student/informacion";
    }
    @RequestMapping("/listWithOutSkp")
    public String mostrarUsuariosNoSkps(HttpSession session, Model model){
        String mensaje = validator.comprobar_conexion(session, model, "/student/listWithOutSkp", true);
        if (!mensaje.equals(""))
            return mensaje;
        List<Student> studentList = studentDao.getStudentsWithOutSkps();
        model.addAttribute("studentList",studentList);
        return "student/listWithOutSkps";
    }
    @RequestMapping("/anular/{idStudent}")
    public String moderarUsuario(HttpSession session, Model model, @PathVariable int idStudent){
        String mensaje = validator.comprobar_conexion(session, model, "/student/anular/"+idStudent, true);
        if (!mensaje.equals(""))
            return mensaje;
        Student student = studentDao.getStudent(idStudent);
        model.addAttribute("student",student);
        return "student/moderar_cuenta";
    }
    @RequestMapping(value = "/anular/{idStudent}",method = RequestMethod.POST)
    public String anularUsuario(HttpSession session, Model model, @PathVariable int idStudent, @ModelAttribute("student") Student studentModel){
        String mensaje = validator.comprobar_conexion(session, model, "/student/anular/"+idStudent, true);
        if (!mensaje.equals(""))
            return mensaje;
        Student student = studentDao.getStudent(idStudent);
        student.setBanReason(studentModel.getBanReason());
        student.setUnavailable(true);
        studentDao.updateStudent(student);
        return "redirect:/student/listWithOutSkp";
    }
    @RequestMapping("/recuperar/{idStudent}")
    public String recuperarUsuario(HttpSession session, Model model, @PathVariable int idStudent){
        String mensaje = validator.comprobar_conexion(session, model, "/student/recuperar/"+idStudent, true);
        if (!mensaje.equals(""))
            return mensaje;
        Student student = studentDao.getStudent(idStudent);
        student.setBanReason("");
        student.setUnavailable(false);
        studentDao.updateStudent(student);
        return "redirect:/student/listWithOutSkp";
    }

}
