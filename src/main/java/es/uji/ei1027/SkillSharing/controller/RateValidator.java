package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.Collaboration;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RateValidator  implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Collaboration.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Collaboration collaboration = (Collaboration) obj;
        if (collaboration.getComments() == null || collaboration.getComments().trim().equals(""))
            errors.rejectValue("comments", "obligatorio", "Hay que introducir un comentario");
        int valoracion = collaboration.getRating();
        if (valoracion < 1 || valoracion > 5)
            errors.rejectValue("rating", "valor incorrecto", "El valor debe ser entre 1 y 5 inclusive");
        int duracion = collaboration.getDuration();
        if (duracion < 0 || duracion > 20)
            errors.rejectValue("duration", "valor incorrecto", "La duración debe ser entre 1 y 20 inclusive");
    }
}
