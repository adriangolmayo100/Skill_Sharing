package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Collaboration;
import org.springframework.jdbc.core.RowMapper;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class CollaborationRowMapper implements RowMapper<Collaboration> {

    public Collaboration mapRow(ResultSet rs, int rowNum) throws SQLException{
        Collaboration collaboration = new Collaboration();
        collaboration.setIdRequest(rs.getInt("idrequest"));
        collaboration.setId_offer(rs.getInt("IdOffer"));
        Date d = rs.getObject("start", Date.class);
        collaboration.setStart((Date) d);
        Date d1 =rs.getObject("finish", Date.class);
        collaboration.setFinish((Date) d1);
        collaboration.setRating(rs.getInt("rating"));
        collaboration.setComments(rs.getString("comments"));
        return collaboration;
    }

}

