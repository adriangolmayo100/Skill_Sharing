package es.uji.ei1027.SkillSharing.RowMappers;

import es.uji.ei1027.SkillSharing.model.SkillType;
import es.uji.ei1027.SkillSharing.model.StatisticStudent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticCollaborationRowMapper implements RowMapper<StatisticStudent> {
    public StatisticStudent mapRow(ResultSet rs, int rowNum) throws SQLException {
        StatisticStudent statisticStudent = new StatisticStudent();
        statisticStudent.setValoracionMedia(rs.getFloat("mean_rating"));
        statisticStudent.setIdStudent(rs.getInt("id_student"));
        statisticStudent.setColaboracionesCumplidasConExito(rs.getInt("collaboration_finished"));
        return statisticStudent;

    }
}
