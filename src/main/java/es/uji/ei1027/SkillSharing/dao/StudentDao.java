package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository // En Spring els DAOs van anotats amb @Repository
public class StudentDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void nuevoStudent(Student student){
        jdbcTemplate.update("INSERT INTO Student VALUES()");
    }
}
