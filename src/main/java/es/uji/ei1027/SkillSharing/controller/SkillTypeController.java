package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.OfferDao;
import es.uji.ei1027.SkillSharing.dao.RequestDao;
import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import es.uji.ei1027.SkillSharing.model.SkillType;
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
@RequestMapping("/skilltype")
public class SkillTypeController {
    @Autowired
    private OfferDao offerDao;
    private SkillTypeDao skillTypeDao;
    @Autowired
    private RequestDao requestDao;
    private UserValidator validator = new UserValidator();
    @Autowired
    public void setRequestDao(SkillTypeDao skillTypeDao) {

        this.skillTypeDao=skillTypeDao;
    }

    @RequestMapping(value = "/anular/{idSkillType}")
    public String anularSkillType(HttpSession session, Model model,@PathVariable int idSkillType) {
        String mensaje = validator.comprobar_conexion(session, model, "/skilltype/anular/"+idSkillType, true);
        if (!mensaje.equals("")){
            return mensaje;
        }
        SkillType skillType = skillTypeDao.getSkillType(idSkillType);
        skillType.setValid(false);
        offerDao.anularOfertasSkillType(idSkillType);
        requestDao.anularDemandasSkillType(idSkillType);
        skillTypeDao.updateSkillType(skillType);
        return "redirect:/skilltype/listWithStatistics";
    }
    @RequestMapping(value = "/recuperar/{idSkillType}")
    public String recuperarSkillType(HttpSession session, Model model, @PathVariable int idSkillType) {
        String mensaje = validator.comprobar_conexion(session, model, "/skilltype/recuperar/"+idSkillType, true);
        if (!mensaje.equals("")){
            return mensaje;
        }
        SkillType skillType = skillTypeDao.getSkillType(idSkillType);
        skillType.setValid(true);
        skillTypeDao.updateSkillType(skillType);
        return "redirect:/skilltype/listWithStatistics";
    }
    @RequestMapping("/list")
    public String listSkillTypes(Model model) {
        model.addAttribute("skills", skillTypeDao.getSkillTypesValid());
        return "skilltype/list";
    }
    @RequestMapping("/listWithStatistics")
    public String listSkillTypesWithStatistics(HttpSession session, Model model) {
        String mensaje = validator.comprobar_conexion(session, model, "/skilltype/listWithStatistics/", true);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("skills", skillTypeDao.getSkillTypeWithStatistics());
        return "skilltype/listWithStatistics";
    }

    @RequestMapping(value="/add")
    public String addRequest(HttpSession session, Model model) {
        String mensaje = validator.comprobar_conexion(session, model,"/skilltype/add", true);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("skilltype", new SkillType());
        return "skilltype/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(HttpSession session, Model model,@ModelAttribute("skilltype") SkillType skillType,
                                   BindingResult bindingResult) {
        String mensaje = validator.comprobar_conexion(session, model, "/skilltype/add", true);
        if (!mensaje.equals("")){
            return mensaje;
        }
        SkillTypeValidator skillTypeValidator = new SkillTypeValidator();
        skillTypeValidator.validate(skillType, bindingResult);
        if (bindingResult.hasErrors())
            return "request/add";
        skillType.setValid(true);
        skillTypeDao.addSkillType(skillType);
        return "redirect:/skilltype/listWithStatistics";
    }

    @RequestMapping(value="/update/{idSkillType}", method = RequestMethod.GET)
    public String editSkillType(HttpSession session, Model model,@PathVariable Integer idSkillType) {
        String mensaje = validator.comprobar_conexion(session, model, "/skilltype/update/"+idSkillType, true);
        if (!mensaje.equals("")){
            return mensaje;
        }
        model.addAttribute("skilltype", skillTypeDao.getSkillType(idSkillType));
        return "skilltype/update";
    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit( HttpSession session, Model model,
            @ModelAttribute("skilltype") SkillType skillType,
            BindingResult bindingResult) {
        String mensaje = validator.comprobar_conexion(session, model, "/skilltype/update/"+skillType.getIdSkillType(), true);
        if (!mensaje.equals("")){
            return mensaje;
        }
        SkillTypeValidator skillTypeValidator = new SkillTypeValidator();
        skillTypeValidator.validate(skillType, bindingResult);
        if (bindingResult.hasErrors())
            return "skilltype/update";
        SkillType skillTypeBBDD = skillTypeDao.getSkillType(skillType.getIdSkillType());
        skillType.setValid(skillTypeBBDD.isValid());
        skillTypeDao.updateSkillType(skillType);
        return "redirect:/skilltype/listWithStatistics";
    }
}
