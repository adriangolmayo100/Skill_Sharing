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

@Controller
@RequestMapping("/skilltype")
public class SkillTypeController {
    @Autowired
    private OfferDao offerDao;
    private SkillTypeDao skillTypeDao;
    @Autowired
    private RequestDao requestDao;
    @Autowired
    public void setRequestDao(SkillTypeDao skillTypeDao) {

        this.skillTypeDao=skillTypeDao;
    }

    @RequestMapping(value = "/delete/{idSkillType}")
    public String processDeleteSkillType(@PathVariable int idSkillType) {
        SkillType skillType = skillTypeDao.getSkillType(idSkillType);
        skillType.setValid(false);
        offerDao.anularOfertasSkillType(idSkillType);
        requestDao.anularDemandasSkillType(idSkillType);
        skillTypeDao.updateSkillType(skillType);
        return "redirect:../list";
    }
    @RequestMapping("/list")
    public String listSkillTypes(Model model) {
        model.addAttribute("skills", skillTypeDao.getSkillTypesValid());
        return "skilltype/list";
    }
    @RequestMapping("/listWithStatistics")
    public String listSkillTypesWithStatistics(Model model) {
        model.addAttribute("skills", skillTypeDao.getSkillTypeWithStatistics());
        return "skilltype/listWithStatistics";
    }

    @RequestMapping(value="/add")
    public String addRequest(Model model) {
        model.addAttribute("skilltype", new SkillType());
        return "skilltype/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("skilltype") SkillType skillType,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "request/add";
        skillTypeDao.addSkillType(skillType);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{idSkillType}", method = RequestMethod.GET)
    public String editSkillType(Model model, @PathVariable Integer idSkillType) {
        model.addAttribute("skilltype", skillTypeDao.getSkillType(idSkillType));
        return "skilltype/update";
    }


    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("skilltype") SkillType skillType,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "skilltype/update";
        skillTypeDao.updateSkillType(skillType);
        return "redirect:list";
    }
}
