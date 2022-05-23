package es.uji.ei1027.SkillSharing.RowMappers;

import es.uji.ei1027.SkillSharing.model.StatisticStudent;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticOffersRequestsRowMapper implements RowMapper<StatisticStudent> {
    public StatisticStudent mapRow(ResultSet rs, int rowNum) throws SQLException {
        StatisticStudent statisticStudent = new StatisticStudent();
        statisticStudent.setOfertasPublicadas(rs.getInt("num_ofertas"));
        statisticStudent.setIdStudent(rs.getInt("id_student"));
        statisticStudent.setDemandasPublicadas(rs.getInt("num_request"));
        return statisticStudent;
    }
}
