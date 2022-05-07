package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import es.uji.ei1027.SkillSharing.model.Request;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RequestValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Request request =(Request) obj;
        if (request.getDescription().trim().equals(""))
            errors.rejectValue("description","obligatorio","Hay que introducir una descripción");
        if (request.getDuration()<=0)
            errors.rejectValue("duration","valor incorrecto","La duració tiene que se > 0");

    }
}