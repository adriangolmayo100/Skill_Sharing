package es.uji.ei1027.SkillSharing.RowMappers;

import es.uji.ei1027.SkillSharing.model.Collaboration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class CollaborationStandarRowMapper implements RowMapper<Collaboration> {

    public Collaboration mapRow(ResultSet rs, int rowNum) throws SQLException{
        Collaboration collaboration = new Collaboration();
        collaboration.setIdRequest(rs.getInt("id_request"));
        collaboration.setIdOffer(rs.getInt("id_offer"));
        collaboration.setStart(rs.getObject("start", LocalDate.class));
        collaboration.setFinish(rs.getObject("finish", LocalDate.class));
        collaboration.setDuration(rs.getInt("duration"));
        collaboration.setRating(rs.getInt("rating"));
        collaboration.setComments(rs.getString("comments"));
        collaboration.setValid(rs.getBoolean("valid"));
        return collaboration;
    }

}