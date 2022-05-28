package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.Request;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Controller
public class RequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Request request = (Request) obj;
        boolean fechas_validas = true;
        if (request.getDescription() == null || request.getDescription().trim().equals(""))
            errors.rejectValue("description", "obligatorio", "Hay que introducir una descripción");
        if (request.getDuration() <= 0)
            errors.rejectValue("duration", "valor incorrecto", "La duración tiene que ser mayor que 0");
        if (request.getStart() == null) {
            errors.rejectValue("start", "obligatorio", "Debe introducir una fecha");
            fechas_validas = false;
        }
        if (request.getFinish() == null) {
            errors.rejectValue("finish", "obligatorio", "Debe introducir una fecha");
            fechas_validas = false;
        }
        if (fechas_validas) {
            LocalDate inicio = request.getStart();
            LocalDate fin = request.getFinish();
            LocalDate fechaActual = java.time.LocalDate.now();
            if (fechaActual.isAfter(inicio))
                errors.rejectValue("start", "valor incorrecto", "La fecha de inicio debe ser posterior a la de hoy");
            if (inicio.isAfter(fin))
                errors.rejectValue("finish", "valores incorrectos", "La fecha de finalización debe ser mayor que la de inicio");
            long diasExtra = request.getDuration() / 24;
            if (inicio.plusDays(diasExtra).isAfter(fin))
                errors.rejectValue("duration", "valor incorrecto", "La duración es muy grande para las fechas seleccionadas");
        }
    }
}