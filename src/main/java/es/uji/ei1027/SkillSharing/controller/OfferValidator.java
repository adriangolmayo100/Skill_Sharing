package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import es.uji.ei1027.SkillSharing.model.Offer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OfferValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Offer.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Offer offer =(Offer) obj;
        if (offer.getDescription().trim().equals(""))
            errors.rejectValue("description","obligatorio","Hay que introducir una descripción");
        if (offer.getDuration()<=0)
            errors.rejectValue("duration","valor incorrecto","La duració tiene que se > 0");

    }
}
