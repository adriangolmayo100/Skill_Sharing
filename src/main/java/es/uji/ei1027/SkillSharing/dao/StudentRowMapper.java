package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Request;
import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setIdStudent(rs.getInt("id_student"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setPostalCode(rs.getInt("postal_code"));
        student.setBalance(rs.getInt("balance"));
        student.setDegree(rs.getString("degree"));
        student.setCourse(rs.getInt("course"));
        student.setSkp(rs.getBoolean("skp"));
        student.setPhoneNumber(rs.getInt("phone_number"));
        student.setGender(rs.getString("gender"));
        return student;
    }
}
