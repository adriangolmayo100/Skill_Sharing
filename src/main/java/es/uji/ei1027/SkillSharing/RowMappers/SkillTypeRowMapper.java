package es.uji.ei1027.SkillSharing.RowMappers;

import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class SkillTypeRowMapper implements RowMapper<SkillType>{

    public SkillType mapRow(ResultSet rs, int rowNum) throws SQLException{

        SkillType skillType = new SkillType();
        skillType.setIdSkillType(rs.getInt("id_skilltype"));
        skillType.setName(rs.getString("name"));
        skillType.setDescription(rs.getString("description"));
        skillType.setLevel(rs.getInt("level"));
        return skillType;

    }
}
