package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.CollaborationDao;
import es.uji.ei1027.SkillSharing.dao.OfferDao;
import es.uji.ei1027.SkillSharing.model.Collaboration;
import es.uji.ei1027.SkillSharing.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/collaboration")
public class CollaborationController {
    private CollaborationDao collaborationDao;


    @Autowired
    public void setCollaborationDao(CollaborationDao collaborationDao){
        this.collaborationDao = collaborationDao;
    }

    @RequestMapping(value = "/delete/{idRequest}/{idOffer}")
    public String processDeleteCollaboration(@PathVariable Integer idRequest,@PathVariable Integer idOffer){
        collaborationDao.deleteCollaboration(idRequest,idOffer);
        return "redirect:../../list";
    }

    @RequestMapping("/list")
    public String listCollaboration(Model model){
        model.addAttribute("collaborations", collaborationDao.getCollaboration());
        return "collaboration/list";
    }

    @RequestMapping(value="/add")
    public String addCollaboration(Model model){
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

    @RequestMapping(value="/update/{idRequest}/{idOffer}", method=RequestMethod.GET)
    public String editCollaboration(Model model,@PathVariable Integer idRequest, @PathVariable Integer idOffer){
        model.addAttribute("collaboration", collaborationDao.getCollaboration(idRequest,idOffer));
        return "collaboration/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("collaboration") Collaboration collaboration,
                                      BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "collaboration/update";
        collaborationDao.updateCollaboration(collaboration);
        return "redirect:list";
    }
}
