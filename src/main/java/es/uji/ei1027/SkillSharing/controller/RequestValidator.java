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
        String[] campos = request.getStart().toString().split("-");
        int inicio = Integer.parseInt(campos[0]) * 365 * 24 + Integer.parseInt(campos[1]) * 30 * 24 + Integer.parseInt(campos[2]) * 24;
        campos = request.getFinish().toString().split("-");
        int fin = Integer.parseInt(campos[0]) * 365 * 24 + Integer.parseInt(campos[1]) * 30 * 24 + Integer.parseInt(campos[2]) * 24;
        if(fin - inicio < 0)
            errors.rejectValue("finish", "valores incorrectos", "La fecha de finalización debe ser mayor que la de inicio");
        if(fin - inicio - request.getDuration() < 0)
            errors.rejectValue("duration", "valor incorrecto", "La duración sobrepasa el tiempo de la oferta");
    }
}