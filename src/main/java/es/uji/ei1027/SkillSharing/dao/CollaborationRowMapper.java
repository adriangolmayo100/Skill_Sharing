package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Collaboration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class CollaborationRowMapper implements RowMapper<Collaboration> {

    public Collaboration mapRow(ResultSet rs, int rowNum) throws SQLException{
        Collaboration collaboration = new Collaboration();
        collaboration.setIdRequest(rs.getInt("id_request"));
        collaboration.setIdOffer(rs.getInt("id_offer"));
        Date d = rs.getObject("start", Date.class);
        collaboration.setStart((Date) d);
        Date d1 =rs.getObject("finish", Date.class);
        collaboration.setFinish((Date) d1);
        collaboration.setIdSkillType(rs.getInt("id_skilltype"));
        collaboration.setDuration(rs.getInt("duration"));
        collaboration.setDescription(rs.getString("description"));
        collaboration.setRating(rs.getInt("rating"));
        collaboration.setComments(rs.getString("comments"));
        return collaboration;
    }

}

