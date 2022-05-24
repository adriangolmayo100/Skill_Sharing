package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SkillTypeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SkillType.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors){
        SkillType skillType = (SkillType) obj;
        if (skillType.getName().trim().equals(""))
            errors.rejectValue("name", "obligatorio", "Hay que introducir un título");
        if (skillType.getDescription().trim().equals(""))
            errors.rejectValue("description", "obligatorio", "Hay que introducir una descripción");
        if (skillType.getLevel() < 0)
            errors.rejectValue("duration", "valor incorrecto", "La duración tiene que ser mayor o igual a 0");
    }
}
