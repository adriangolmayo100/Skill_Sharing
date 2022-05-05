package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.CollaborationDao;
import es.uji.ei1027.SkillSharing.dao.OfferDao;
import es.uji.ei1027.SkillSharing.dao.RequestDao;
import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
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
@RequestMapping("/offer")
public class OfferController {

    private OfferDao offerDao;
    private SkillTypeDao skillTypeDao;
    private RequestDao requestDao;
    private CollaborationDao collaborationDao;


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
    public String processDeleteOffer(@PathVariable Integer idOffer){
        offerDao.deleteOffer(idOffer);
        return "redirect:../../list";
    }


    @RequestMapping("/list")
    public String listOffer(Model model, HttpSession session){
        Student student = (Student) session.getAttribute("student");
        model.addAttribute("offers", offerDao.getValidOffers());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "offer/list";
    }


    @RequestMapping(value="/add")
    public String addOffer(Model model){
        model.addAttribute("offer", new Offer());
        return "offer/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("offer") Offer offer,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "offer/add";
        offerDao.addOffer(offer);
        return "redirect:list";
    }
    @RequestMapping(value="/accept/{id}", method=RequestMethod.GET)
    public String accept(HttpSession session, Model model, @PathVariable Integer id) {
        Student student= (Student) session.getAttribute("student");
        if ( student == null)
        {
            session.setAttribute("nextUrl","/offer/accept/"+id);
            model.addAttribute("student", new Student());
            return "login";
        }
        Offer offer = offerDao.getOffer(id);
        offer.setValid(false);
        offerDao.updateOffer(offer);
        Request request = new Request();
        request.setIdStudent(student.getIdStudent());
        request.createRequestForOffer(offer);
        requestDao.addRequest(request);
        Collaboration collaboration = new Collaboration();
        collaboration.createCollaboration(offer,request);
        collaborationDao.addCollaboration(collaboration);
        return "redirect:../list";
    }

    @RequestMapping(value="/update/{idOffer}", method=RequestMethod.GET)
    public String editOffer(Model model, @PathVariable Integer idOffer){
        model.addAttribute("offer", offerDao.getOffer(idOffer));
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
        model.addAttribute("offer",offerDao.getOffers());
        return "offer/mis_ofertas";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("offer") Offer offer,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "offer/update";
        offerDao.updateOffer(offer);
        return "redirect:list";
    }
}
