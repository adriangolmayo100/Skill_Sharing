package es.uji.ei1027.SkillSharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    /*@RequestMapping(value = "/default_correcto")
    public String dafaultCorrecto(){
        return "/feedback/default_correcto";
    }*/

    @RequestMapping(value = "/offer_correcto")
    public String offerCorrecto(){
        return "/feedback/offer_correcto";
    }

    @RequestMapping(value = "/request_correcto")
    public String requestCorrecto(){
        return "/feedback/request_correcto";
    }

    @RequestMapping(value = "/collaboration_error")
    public String collaborationError(){
        return "/feedback/collaboration_error";
    }

    @RequestMapping(value = "/collaboration_ecorrecto")
    public String collaborationCorrecto(){
        return "/feedback/collaboration_correcto";
    }
}
