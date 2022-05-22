package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.*;
import es.uji.ei1027.SkillSharing.model.Collaboration;
import es.uji.ei1027.SkillSharing.model.Offer;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/collaboration")
public class CollaborationController {
    private UserValidator validator = new UserValidator();
    private CollaborationDao collaborationDao;
    @Autowired
    private SkillTypeDao skillTypeDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private OfferDao offerDao;
    @Autowired
    private RequestDao requestDao;


    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao){
        this.collaborationDao = collaborationDao;
    }

    @RequestMapping(value = "/delete/{idOffer}/{idRequest}")
    public String processDeleteCollaboration(@PathVariable Integer idRequest,@PathVariable Integer idOffer){
        collaborationDao.deleteCollaboration(idRequest,idOffer);
        return "redirect:/collaboration/mis_request_colaboraciones";
    }
  @RequestMapping(value = "/accept/{idOffer}/{idRequest}")
    public String acceptCollaboration(@PathVariable Integer idRequest,@PathVariable Integer idOffer){
        Collaboration collaboration = collaborationDao.getCollaboration(idRequest,idOffer);
        collaboration.setValid(true);
        collaborationDao.updateCollaboration(collaboration);
        return "redirect:/collaboration/mis_colaboraciones";
    }

    @RequestMapping("/list")
    public String listCollaboration(HttpSession session, Model model){
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}", false);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("collaborations", collaborationDao.getCollaborations());
        model.addAttribute("collaborationsRequest", collaborationDao.getCollaborations());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "collaboration/list";
    }
    @RequestMapping("/statistics")
    public String listCollaborationStatistic(HttpSession session, Model model){
        String mensaje = validator.comprobar_conexion(session, model, "/collaboration/statistics/", true);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("students", studentDao.getStudents());
        model.addAttribute("collaborations", collaborationDao.getCollaborations());
        model.addAttribute("collaborationsRequest", collaborationDao.getRequestCollaborations());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "collaboration/statistics";
    }
    @RequestMapping("/mis_colaboraciones")
    public String listMisCollaboration(HttpSession session, Model model){
        Student student = (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/collaboration/mis_colaboraciones", false);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("students", studentDao.getStudents());
        model.addAttribute("collaborationsImRequest", collaborationDao.getMyCollaborationsWhenRequest(student.getIdStudent()));
        model.addAttribute("collaborationsImOffer", collaborationDao.getMyCollaborationsWhenOffer(student.getIdStudent()));
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        model.addAttribute("today", LocalDate.now());
        return "collaboration/mis_colaboraciones";
    }
  @RequestMapping("/mis_request_colaboraciones")
    public String listMisRequestCollaboration(HttpSession session, Model model){
        Student student = (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/collaboration/mis_request_colaboraciones", false);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("students", studentDao.getStudents());
        model.addAttribute("collaborationsImRequest", collaborationDao.getMyRequestCollaborationsWhenRequest(student.getIdStudent()));
        model.addAttribute("collaborationsImOffer", collaborationDao.getMyRequestCollaborationsWhenOffer(student.getIdStudent()));
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "collaboration/mis_request_colaboraciones";
    }

    @RequestMapping(value="/add")
    public String addCollaboration(HttpSession session, Model model){
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}", false);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("collaboration", new Collaboration());
        return "collaboration/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("collaboration") Collaboration collaboration,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "collaboration/add";
        collaborationDao.addCollaboration(collaboration);
        return "redirect:list";
    }
    @RequestMapping(value="/rate/{idRequest}/{idOffer}")
    public String addRate(HttpSession session, Model model,@PathVariable Integer idRequest,@PathVariable Integer idOffer){
        String mensaje = validator.comprobar_conexion(session, model, "/collaboration/rate/"+idRequest+"/"+idOffer, false);
        if (!mensaje.equals("")){
            return mensaje;
        }
        Collaboration collaboration = collaborationDao.getCollaboration(idRequest,idOffer);
        model.addAttribute("collaboration",collaboration );
        return "/collaboration/rate";
    }
    @RequestMapping(value="/rate/{idRequest}/{idOffer}",method= RequestMethod.POST)
    public String putRate(HttpSession session, Model model, @ModelAttribute("collaboration") Collaboration collaborationModel,Offer offerModel, @PathVariable Integer idRequest, @PathVariable Integer idOffer){
        String mensaje = validator.comprobar_conexion(session, model, "/collaboration/rate/"+idRequest+"/"+idOffer, false);
        if (!mensaje.equals("")){
            return mensaje;
        }
        Collaboration collaboration=collaborationDao.getCollaboration(idRequest,idOffer);
        collaboration.setRating(collaborationModel.getRating());
        collaboration.setDuration(collaborationModel.getDuration());
        collaboration.setComments(collaborationModel.getComments());
        Student studentRequests = studentDao.obtenerStudent(requestDao.getRequest(idRequest).getIdStudent());
        Student studentOffers = studentDao.obtenerStudent(offerDao.getOffer(idOffer).getIdStudent());
        studentOffers.setHoursGiven(studentOffers.getHoursGiven()+collaboration.getDuration());
        studentRequests.setHoursReceived(studentRequests.getHoursReceived()+collaboration.getDuration());
        studentDao.updateStudent(studentOffers);
        studentDao.updateStudent(studentRequests);
        collaborationDao.updateCollaboration(collaboration);
        return "redirect:/collaboration/mis_colaboraciones";
    }

    @RequestMapping(value="/update/{idRequest}/{idOffer}", method=RequestMethod.GET)
    public String editCollaboration(HttpSession session, Model model,@PathVariable Integer idRequest, @PathVariable Integer idOffer){
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}", false);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("collaboration", collaborationDao.getCollaboration(idRequest,idOffer));
        return "collaboration/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(HttpSession session, Model model, @ModelAttribute("collaboration") Collaboration collaboration,
                                      BindingResult bindingResult){
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}", false);
        if (!mensaje.equals("")){
            return mensaje;
        }
        if (bindingResult.hasErrors())
            return "collaboration/update";
        collaborationDao.updateCollaboration(collaboration);
        return "redirect:list";
    }
}
