package es.uji.ei1027.SkillSharing.RowMappers;

import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillTypeStatisticsRowMapper implements RowMapper<SkillType> {
    public SkillType mapRow(ResultSet rs, int rowNum) throws SQLException {

        SkillType skillType = new SkillType();
        skillType.setIdSkillType(rs.getInt("id_skilltype"));
        skillType.setName(rs.getString("name"));
        skillType.setDescription(rs.getString("description"));
        skillType.setLevel(rs.getInt("level"));
        skillType.setNumberOfOffers(rs.getInt("number_offers"));
        skillType.setNumberOfRequest(rs.getInt("number_requests"));
        skillType.setValid(rs.getBoolean("validSkillType"));
        return skillType;

    }
}
