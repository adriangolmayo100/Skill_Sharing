package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.CollaborationDao;
import es.uji.ei1027.SkillSharing.dao.OfferDao;
import es.uji.ei1027.SkillSharing.dao.RequestDao;
import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import es.uji.ei1027.SkillSharing.model.Collaboration;
import es.uji.ei1027.SkillSharing.model.Offer;
import es.uji.ei1027.SkillSharing.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;
    private SkillTypeDao skillTypeDao;
    private OfferDao offerDao;
    private CollaborationDao collaborationDao;


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
    public String accept(Model model, @PathVariable Integer idRequest) {
        Request request = requestDao.getRequest(idRequest);
        request.setValid(false);
        Offer offer = new Offer();
        offer.createOfferForRequest(request);
        requestDao.updateRequest(request);
        offerDao.addOffer(offer);
        Collaboration collaboration = new Collaboration();
        collaboration.createCollaboration(offer,request);
        collaborationDao.addCollaboration(collaboration);
        return "redirect:../list";
    }
    @RequestMapping(value = "/delete/{idRequest}")
    public String processDeleteRequest(@PathVariable Integer idRequest){
        requestDao.deleteRequest(idRequest);
        return "redirect:../../list";
    }


    @RequestMapping("/list")
    public String listRequest(Model model){
        model.addAttribute("requests", requestDao.getValidRequests());
        model.addAttribute("skillTypes", skillTypeDao.getSkillTypes());
        return "request/list";
    }

    @RequestMapping(value="/add")
    public String addRequest(Model model){
        model.addAttribute("request", new Request());
        return "request/add";
    }
    @RequestMapping(value="/mis_demandas")
    public String mis_requests(Model model){
        model.addAttribute("request",requestDao.getRequests());
        return "request/mis_demandas";
    }
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("request") Request request,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "request/add";
        requestDao.addRequest(request);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idRequest}", method=RequestMethod.GET)
    public String editRequest(Model model, @PathVariable Integer idRequest){
        model.addAttribute("request", requestDao.getRequest(idRequest));
        return "request/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("request") Request request,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "request/update";
        requestDao.updateRequest(request);
        return "redirect:list";
    }
}
