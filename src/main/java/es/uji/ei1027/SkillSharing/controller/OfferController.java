package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.*;
import es.uji.ei1027.SkillSharing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private StudentDao studentDao;
    private OfferDao offerDao;
    private SkillTypeDao skillTypeDao;
    private RequestDao requestDao;
    private CollaborationDao collaborationDao;
    private UserValidator validator = new UserValidator();


    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao){
        this.collaborationDao = collaborationDao;
    }
    @Autowired
    public void setRequestDao(RequestDao requestDao){
        this.requestDao = requestDao;
    }
    @Autowired
    public void setOfferDao(OfferDao offerDao){
        this.offerDao = offerDao;
    }
    @Autowired
    public void setSkillTypeDao(SkillTypeDao skillType){
        this.skillTypeDao = skillType;
    }

    @RequestMapping(value = "/delete/{idOffer}")
    public String processDeleteOffer(HttpSession session, Model model, @PathVariable Integer idOffer){
        String mensaje = validator.comprobar_conexion(session, model, "/offer/delete/"+idOffer);
        if (!mensaje.equals("")){
            return mensaje;
        }
        offerDao.deleteOffer(idOffer);
        return "feedback/offer_correcto";
    }


    @RequestMapping("/list")
    public String listOffers(Model model, HttpSession session){
        model.addAttribute("students",studentDao.getStudents());
        model.addAttribute("offers", offerDao.getValidOffers());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "offer/list";
    }

    @RequestMapping("/list/{idRequest}")
    public String listOffers(HttpSession session, Model model,@PathVariable Integer idRequest){
        String mensaje = validator.comprobar_conexion(session, model, "/offer/list/"+idRequest);
        if (!mensaje.equals("")){
            return mensaje;
        }
        Request request = requestDao.getRequest(idRequest);
        model.addAttribute("requestSearch", request);
        model.addAttribute("offers", offerDao.getValidOffers(request));
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        model.addAttribute("students",studentDao.getStudents());
        return "offer/listBySkillType";
    }
    @RequestMapping(value="/add")
    public String addOffer(HttpSession session,Model model){
        String mensaje = validator.comprobar_conexion(session, model, "/offer/add");
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("offer", new Offer());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "offer/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("offer") Offer offer,@PathParam("start") String start, Model model,
                                   BindingResult bindingResult,HttpSession session) {
        System.out.println(start);
        String mensaje = validator.comprobar_conexion(session, model, "/offer/add");
        if (!mensaje.equals("")){
            return mensaje;
        }
        OfferValidator offerValidator = new OfferValidator();
        offerValidator.validadorFecha(offer, start, true, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
            return "offer/add";
        }
        offerValidator.validate(offer,bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
            return "offer/add";
        }
        Student student= (Student) session.getAttribute("student");
        //offer.setStart(LocalDate.parse(start));
        offer.setValid(true);
        offer.setIdStudent(student.getIdStudent());
        System.out.println(offer.toString());
        model.addAttribute("studentsEmail",requestDao.getStudentsWithIdSkillType(offer.getIdSkillType(),offer.getIdStudent()));
        return "offer/correcto";
    }
    @RequestMapping(value="/accept/{id}", method=RequestMethod.GET)
    public String acceptOffer(HttpSession session, Model model, @PathVariable Integer id) {
        Student student= (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/accept/"+id);
        if (!mensaje.equals("")){
            return mensaje;
        }
        Offer offer = offerDao.getOffer(id);
        if (student.getIdStudent()!=offer.getIdStudent()){
            offer.setValid(false);
            offerDao.updateOffer(offer);
            Request request = new Request();
            request.setIdStudent(student.getIdStudent());
            request.createRequestForOffer(offer);
            requestDao.addRequest(request);
            Collaboration collaboration = new Collaboration();
            collaboration.createCollaboration(offer,request.getIdRequest());
            collaborationDao.addCollaboration(collaboration);
            return "/feedback/collaboration_correcto";
        }
        return "/feedback/collaboration_erroneo";
    }
    @RequestMapping(value="/accept/{idOffer}/{idRequest}", method=RequestMethod.GET)
    public String accept(HttpSession session, Model model, @PathVariable Integer idOffer,@PathVariable Integer idRequest) {
        Student student= (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/offer/accept/"+idOffer+"/"+idRequest);
        if (!mensaje.equals("")){
            return mensaje;
        }
        Offer offer = offerDao.getOffer(idOffer);
        Request request = requestDao.getRequest(idRequest);
        if (student.getIdStudent()!=offer.getIdStudent() && collaborationDao.getCollaboration(idRequest,idOffer)==null){
            offerDao.updateOffer(offer);
            requestDao.updateRequest(request);
            Collaboration collaboration = new Collaboration();
            collaboration.createCollaboration(offer,request.getIdRequest());
            collaboration.setValid(false);
            collaborationDao.addCollaboration(collaboration);
            model.addAttribute("student",studentDao.getStudent(offer.getIdStudent()));
            return "/feedback/collaboration_correcto";
        }
        return "/feedback/collaboration_erroneo";
    }

    @RequestMapping(value="/update/{idOffer}", method=RequestMethod.GET)
    public String editOffer(HttpSession session, Model model, @PathVariable Integer idOffer){
        String mensaje = validator.comprobar_conexion(session, model, "/update/"+idOffer);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("offer", offerDao.getOffer(idOffer));
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "offer/update";
    }
    @RequestMapping(value="/mis_ofertas")
    public String mis_ofertas(HttpSession session,Model model){
        Student student= (Student) session.getAttribute("student");
        if ( student == null)
        {
            session.setAttribute("nextUrl","/offer/mis_ofertas");
            model.addAttribute("student", new Student());
            return "login";
        }
        List<SkillType> skillTypeList = skillTypeDao.getSkillTypes();
        model.addAttribute("skillTypes", skillTypeList);
        model.addAttribute("offers",offerDao.getOffers(student.getIdStudent()));
        return "offer/mis_ofertas";
    }
    @RequestMapping(value="/update/{idOffer}", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("offer") Offer offerModel,
                                      BindingResult bindingResult,@PathVariable Integer idOffer){
        if (bindingResult.hasErrors())
            return "offer/update";
        Offer offer = offerDao.getOffer(idOffer);
        offer.updateOffer(offerModel);
        offerDao.updateOffer(offer);
        return "feedback/offer_correcto";
    }

}
