package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.Offer;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.LocalDate;


public class OfferValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Offer.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Offer offer = (Offer) obj;
        boolean fechas_validas = true;
        if (offer.getDescription().trim().equals(""))
            errors.rejectValue("description", "obligatorio", "Hay que introducir una descripción");
        if (offer.getDuration() <= 0)
            errors.rejectValue("duration", "valor incorrecto", "La duración tiene que ser mayor 0");
        if (offer.getStart() == null) {
            errors.rejectValue("start", "obligatorio", "Debe introducir una fecha");
            fechas_validas = false;
        }
        if (offer.getFinish() == null) {
            errors.rejectValue("finish", "obligatorio", "Debe introducir una fecha");
            fechas_validas = false;
        }
        if (fechas_validas) {
            LocalDate inicio = offer.getStart();
            LocalDate fin = offer.getFinish();
            LocalDate fechaActual = java.time.LocalDate.now();
            if (fechaActual.isAfter(inicio))
                errors.rejectValue("start", "valor incorrecto", "La fecha de inicio debe ser posterior a la de hoy");
            if (inicio.isAfter(fin))
                errors.rejectValue("finish", "valores incorrectos", "La fecha de finalización debe ser mayor que la de inicio");
            long diasExtra = offer.getDuration() / 24;
            if (inicio.plusDays(diasExtra).isAfter(fin))
                errors.rejectValue("duration", "valor incorrecto", "La duración es muy grande para las fechas seleccionadas");
        }
    }

}
