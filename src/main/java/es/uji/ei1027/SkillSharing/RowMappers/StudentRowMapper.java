package es.uji.ei1027.SkillSharing.RowMappers;

import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setIdStudent(rs.getInt("id_student"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setUsername(rs.getString("username"));
        student.setPassword(rs.getString("password"));
        student.setCodePostal(rs.getInt("postal_code"));
        student.setHoursGiven(rs.getInt("hours_given"));
        student.setHoursReceived(rs.getInt("hours_received"));
        student.setDegree(rs.getString("degree"));
        student.setCourse(rs.getInt("course"));
        student.setSkp(rs.getBoolean("skp"));
        student.setNumberPhone(rs.getInt("phone_number"));
        student.setGender(rs.getString("gender"));
        student.setUnavailable(rs.getBoolean("unavailable"));
        student.setBanReason(rs.getString("reason_ban"));
        return student;
    }
}
