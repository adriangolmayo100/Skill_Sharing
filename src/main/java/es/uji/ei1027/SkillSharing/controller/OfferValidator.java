package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.dao.SkillTypeDao;
import es.uji.ei1027.SkillSharing.model.Offer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.List;

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
            errors.rejectValue("duration","valor incorrecto","La duración tiene que se > 0");
        String[] campos = offer.getStart().toString().split("-");
        int inicio = Integer.parseInt(campos[0]) * 365 * 24 + Integer.parseInt(campos[1]) * 30 * 24 + Integer.parseInt(campos[2]) * 24;
        campos = offer.getFinish().toString().split("-");
        int fin = Integer.parseInt(campos[0]) * 365 * 24 + Integer.parseInt(campos[1]) * 30 * 24 + Integer.parseInt(campos[2]) * 24;
        campos = java.time.LocalDate.now().toString().split("-");
        int fecha_actual = Integer.parseInt(campos[0]) * 365 * 24 + Integer.parseInt(campos[1]) * 30 * 24 + Integer.parseInt(campos[2]) * 24;
        if(inicio <= fecha_actual)
            errors.rejectValue("start", "valor incorrecto", "La fecha de inicio debe ser posterior a la de hoy");
        if(fin - inicio < 0)
            errors.rejectValue("finish", "valores incorrectos", "La fecha de finalización debe ser mayor que la de inicio");
        if(fin - inicio - offer.getDuration() < 0)
            errors.rejectValue("duration", "valor incorrecto", "La duración es muy grande para las fechas seleccionadas");
    }
}
