package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.*;
import es.uji.ei1027.SkillSharing.model.Collaboration;
import es.uji.ei1027.SkillSharing.model.Offer;
import es.uji.ei1027.SkillSharing.model.Request;
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


@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;
    private SkillTypeDao skillTypeDao;
    private OfferDao offerDao;
    @Autowired
    private StudentDao studentDao;
    private CollaborationDao collaborationDao;
    private UserValidator validator = new UserValidator();


    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao){
        this.collaborationDao = collaborationDao;
    }
    @Autowired
    public void setOfferDao(OfferDao offerDao){
        this.offerDao = offerDao;
    }
    @Autowired
    public void setRequestDao(RequestDao requestDao){
        this.requestDao = requestDao;
    }
    @Autowired
    public void setSkillTypeDao(SkillTypeDao skillType){
        this.skillTypeDao = skillType;
    }
    @RequestMapping(value="/accept/{idRequest}", method=RequestMethod.GET)
    public String acceptRequest(HttpSession session, Model model, @PathVariable Integer idRequest) {
        Student student= (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}");
        if (!mensaje.equals("")){
            return mensaje;
        }
        Request request = requestDao.getRequest(idRequest);
        if (student.getIdStudent()!=request.getIdStudent()){
            Student studentOffers = student;
            Student studentRequests = studentDao.obtenerStudent(request.getIdStudent());
            studentOffers.setHoursGiven(studentOffers.getHoursGiven()+request.getDuration());
            studentRequests.setHoursReceived(studentRequests.getHoursReceived()+request.getDuration());
            studentDao.updateStudent(studentOffers);
            studentDao.updateStudent(studentRequests);
            request.setValid(false);
            requestDao.updateRequest(request);
            Offer offer = new Offer();
            offer.createOfferForRequest(request);
            offer.setIdStudent(student.getIdStudent());
            offerDao.addOffer(offer);
            Collaboration collaboration = new Collaboration();
            collaboration.createCollaboration(request,offer.getIdOffer());
            collaborationDao.addCollaboration(collaboration);
        }
        return "redirect:../list";
    }
    @RequestMapping(value = "/delete/{idRequest}")
    public String processDeleteRequest(HttpSession session, Model model, @PathVariable Integer idRequest){
        String mensaje = validator.comprobar_conexion(session, model, "/request/delete/"+idRequest);
        if (!mensaje.equals("")){
            return mensaje;
        }
        requestDao.deleteRequest(idRequest);
        return "redirect:../mis_demandas";
    }
    @RequestMapping("/list/{idOffer}")
    public String listRequests(Model model,@PathVariable Integer idOffer){
        Offer offer = offerDao.getOffer(idOffer);
        model.addAttribute("offerSearch",offer);
        model.addAttribute("requests", requestDao.getValidRequests(offer.getIdSkillType()));
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        model.addAttribute("students",studentDao.getStudents());

        return "request/listBySkillType";
    }
    @RequestMapping("/list")
    public String listRequests(Model model){
        model.addAttribute("students",studentDao.getStudents());
        model.addAttribute("requests", requestDao.getValidRequests());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "request/list";
    }

    @RequestMapping(value="/add")
    public String addRequest(HttpSession session,Model model){
        String mensaje = validator.comprobar_conexion(session, model, "/request/add");
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("request", new Request());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "request/add";
    }
    @RequestMapping(value="/mis_demandas")
    public String mis_requests(HttpSession session,Model model){
        Student student= (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/request/mis_demandas");
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        model.addAttribute("requests",requestDao.getRequests(student.getIdStudent()));
        return "request/mis_demandas";
    }
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("request") Request request,Model model,
                                   BindingResult bindingResult,HttpSession session) {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validate(request,bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
            return "request/add";
        }
        Student student= (Student) session.getAttribute("student");
        request.setValid(true);
        request.setIdStudent(student.getIdStudent());
        requestDao.addRequest(request);
        return "redirect:correcto";
    }

    @RequestMapping(value="/update/{idRequest}", method=RequestMethod.GET)
    public String editRequest(HttpSession session, Model model, @PathVariable Integer idRequest){
        String mensaje = validator.comprobar_conexion(session, model, "/update/"+idRequest);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("request", requestDao.getRequest(idRequest));
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "request/update";
    }

    @RequestMapping(value="/update/{id_request}", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("request") Request requestModel, @PathVariable int id_request,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "request/update";
        Request request= requestDao.getRequest(id_request);
        request.updateRequest(requestModel);
        requestDao.updateRequest(request);
        return "request/correcto";
    }
    @RequestMapping(value="/accept/{idRequest}/{idOffer}", method=RequestMethod.GET)
    public String accept(HttpSession session, Model model, @PathVariable Integer idRequest,@PathVariable Integer idOffer) {
        Student student= (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/request/accept/"+idRequest+"/"+idOffer);
        if (!mensaje.equals("")){
            return mensaje;
        }
        Offer offer = offerDao.getOffer(idOffer);
        Request request = requestDao.getRequest(idRequest);
        if (student.getIdStudent()!=offer.getIdStudent()){
            Student studentRequests = studentDao.obtenerStudent(request.getIdStudent());
            Student studentOffers = studentDao.obtenerStudent(offer.getIdStudent());
            studentOffers.setHoursGiven(studentOffers.getHoursGiven()+offer.getDuration());
            studentRequests.setHoursReceived(studentRequests.getHoursReceived()+offer.getDuration());
            studentDao.updateStudent(studentOffers);
            studentDao.updateStudent(studentRequests);
            offer.setValid(false);
            offerDao.updateOffer(offer);
            request.setValid(false);
            requestDao.updateRequest(request);
            Collaboration collaboration = new Collaboration();
            collaboration.createCollaboration(request,offer.getIdOffer());
            collaborationDao.addCollaboration(collaboration);
            System.out.println();
        }
        return "redirect:/request/mis_demandas";
    }
    @RequestMapping(value="/correcto")
    public String operacion_correcta(){
        return "request/correcto";
    }
}
