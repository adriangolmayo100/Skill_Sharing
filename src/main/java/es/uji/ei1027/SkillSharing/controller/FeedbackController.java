package es.uji.ei1027.SkillSharing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @RequestMapping(value = "/offer_correcto")
    public String offerCorrecto(){
        return "/feedback/offer_correcto";
    }

    @RequestMapping(value = "/offer_propio")
    public String offerPropio(){
        return "/feedback/offer_propio";
    }
    @RequestMapping(value = "/request_correcto")
    public String requestCorrecto(){
        return "/feedback/request_correcto";
    }

    @RequestMapping(value = "/request_propio")
    public String requestPropio(){
        return "/feedback/request_propio";
    }
    @RequestMapping(value = "/skp_correcto")
    public String skpCorrecto(){
        return "/feedback/skp_correcto";
    }

    @RequestMapping(value = "/collaboration_correcto")
    public String collaborationCorrecto(){
        return "/feedback/collaboration_correcto";
    }
}
