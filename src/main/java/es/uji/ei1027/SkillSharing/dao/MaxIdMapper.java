package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class MaxIdMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("max_id");
    }
}
