package es.uji.ei1027.SkillSharing.controller;

import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegisterValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Student.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Student student = (Student) obj;
        if (student.getName().trim().equals(""))
            errors.rejectValue("name", "obligatorio", "Debe introducir un nombre");
        if (student.getEmail().trim().equals(""))
            errors.rejectValue("email", "obligatorio", "Debe introducir un correo");
        if (student.getUsername().trim().equals(""))
            errors.rejectValue("username", "obligatorio", "Debe introducir un nombre de usuario");
        if (student.getPassword().trim().equals(""))
            errors.rejectValue("password", "obligatorio", "Debe introducir una contraseña");
        if (student.getDegree().trim().equals(""))
            errors.rejectValue("degree", "obligatorio", "Debe introducir un grado");
        if (student.getCodePostal() <= 0)
            errors.rejectValue("codePostal", "valor incorrecto", "Debe introducir un código postal válido");
        if (student.getCourse() <= 0)
            errors.rejectValue("course", "valor incorrecto", "Debe introducir un curso válido");
        if (student.getNumberPhone() <= 99999999)
            errors.rejectValue("numberPhone", "valor incorrecto", "Debe introducir un número de teléfono válido");

    }
}
