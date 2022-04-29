package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.RequestDao;
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


    @Autowired
    public void setRequestDao(RequestDao requestDao){
        this.requestDao = requestDao;
    }

    @RequestMapping(value = "/delete/{idRequest}")
    public String processDeleteRequest(@PathVariable Integer idRequest){
        requestDao.deleteRequest(idRequest);
        return "redirect:../../list";
    }


    @RequestMapping("/list")
    public String listRequest(Model model){
        model.addAttribute("requests", requestDao.getRequests());
        return "request/list";
    }

    @RequestMapping(value="/add")
    public String addRequest(Model model){
        model.addAttribute("request", new Request());
        return "request/add";
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