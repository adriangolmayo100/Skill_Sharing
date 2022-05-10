package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Request;
import es.uji.ei1027.SkillSharing.model.SkillType;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.jasypt.util.password.BasicPasswordEncryptor;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring els DAOs van anotats amb @Repository
public class StudentDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

    public void nuevoStudent(Student student){
        String contrasenaEncriptada = encryptor.encryptPassword(student.getPassword());
        jdbcTemplate.update("INSERT INTO Student VALUES(?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?)",
                student.getIdStudent(), student.getName(), student.getEmail(), student.getUsername(),
                contrasenaEncriptada, student.getCodePostal(), student.getHoursGiven(),student.getHoursReceived(), student.getDegree(),
                student.getCourse(), student.isSkp(), student.getNumberPhone(), student.getGender(),student.isUnavailable());
    }

    public Student obtenerStudent(String studentId){
        try{
            return jdbcTemplate.queryForObject("SELECT * from Student WHERE id_student=?",
                    new StudentRowMapper(), studentId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Student obtenerStudentConUser(String username){
        try{
            return jdbcTemplate.queryForObject("SELECT * from Student WHERE username=?",
                    new StudentRowMapper(), username);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    public Student loadStudent(String username,String password){
        Student student;
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        try{
            student = jdbcTemplate.queryForObject("SELECT * from Student WHERE username=?",
                    new StudentRowMapper(), username);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
        if (passwordEncryptor.checkPassword(password, student.getPassword()))
            return student;
        return null;
    }


    public Integer getNextId(){
        try{
            return jdbcTemplate.queryForObject("SELECT MAX(id_offer) AS max_id FROM offer",new MaxIdMapper()) + 1;
        }catch(EmptyResultDataAccessException e){
            return -1;
        }
    }
    public List<Student> getStudents() {
        try {
            return jdbcTemplate.query("SELECT * from students",
                    new StudentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Student>();
        }
    }
}
