package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.*;
import es.uji.ei1027.SkillSharing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
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
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}");
        if (!mensaje.equals("")){
            return mensaje;
        }
        offerDao.deleteOffer(idOffer);
        return "redirect:../mis_ofertas";
    }


    @RequestMapping("/list")
    public String listOffers(Model model){
        model.addAttribute("offers", offerDao.getValidOffers());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "offer/list";
    }

    @RequestMapping("/list/{id}")
    public String listOffers(Model model,@PathVariable Integer idRequest){
        Request request = requestDao.getRequest(idRequest);
        model.addAttribute("requestSearch", request);
        model.addAttribute("offers", offerDao.getValidOffers(request.getIdSkillType()));
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "offer/listBySkillType";
    }
    @RequestMapping(value="/add")
    public String addOffer(HttpSession session,Model model){
        String mensaje = validator.comprobar_conexion(session, model, "offer/add");
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("offer", new Offer());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "offer/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("offer") Offer offer, Model model,
                                   BindingResult bindingResult,HttpSession session) {
        OfferValidator offerValidator = new OfferValidator();
        offerValidator.validate(offer,bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
            return "offer/add";
        }
        Student student= (Student) session.getAttribute("student");
        offer.setValid(true);
        offer.setIdStudent(student.getIdStudent());
        offerDao.addOffer(offer);
        return "redirect:/offer/mis_ofertas";
    }
    @RequestMapping(value="/accept/{id}", method=RequestMethod.GET)
    public String acceptOffer(HttpSession session, Model model, @PathVariable Integer id) {
        Student student= (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}");
        if (!mensaje.equals("")){
            return mensaje;
        }
        Offer offer = offerDao.getOffer(id);
        if (student.getIdStudent()!=offer.getIdStudent()){
            Student studentRequests = student;
            Student studentOffers = studentDao.obtenerStudent(offer.getIdStudent());
            studentOffers.setHoursGiven(studentOffers.getHoursGiven()+offer.getDuration());
            studentRequests.setHoursReceived(studentRequests.getHoursReceived()+offer.getDuration());
            studentDao.updateStudent(studentOffers);
            studentDao.updateStudent(studentRequests);
            offer.setValid(false);
            offerDao.updateOffer(offer);
            Request request = new Request();
            request.setIdStudent(student.getIdStudent());
            request.createRequestForOffer(offer);
            requestDao.addRequest(request);
            Collaboration collaboration = new Collaboration();
            collaboration.createCollaboration(offer,request);
            collaborationDao.addCollaboration(collaboration);
        }
        return "redirect:../list";
    }
    @RequestMapping(value="/accept/{id_offer}/{id_request}", method=RequestMethod.GET)
    public String accept(HttpSession session, Model model, @PathVariable Integer id_offer,@PathVariable Integer id_request) {
        Student student= (Student) session.getAttribute("student");
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}");
        if (!mensaje.equals("")){
            return mensaje;
        }
        Offer offer = offerDao.getOffer(id_offer);
        Request request = requestDao.getRequest(id_request);
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
            collaboration.createCollaboration(offer,request);
            collaborationDao.addCollaboration(collaboration);
        }
        return "redirect:../list";
    }

    @RequestMapping(value="/update/{idOffer}", method=RequestMethod.GET)
    public String editOffer(HttpSession session, Model model, @PathVariable Integer idOffer){
        String mensaje = validator.comprobar_conexion(session, model, "/accept/{id}");
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
        return "redirect:../mis_ofertas";
    }
}
