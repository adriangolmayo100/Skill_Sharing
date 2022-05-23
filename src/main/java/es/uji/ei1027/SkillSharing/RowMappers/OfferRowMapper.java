package es.uji.ei1027.SkillSharing.RowMappers;

import es.uji.ei1027.SkillSharing.model.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

public final class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setIdOffer(rs.getInt("id_offer"));
        offer.setIdStudent(rs.getInt("id_student"));
        offer.setIdSkillType(rs.getInt("id_skilltype"));
        offer.setDescription(rs.getString("description"));
        offer.setStart(rs.getObject("start", LocalDate.class));
        offer.setFinish(rs.getObject("finish", LocalDate.class));
        offer.setDuration(rs.getInt("duration"));
        offer.setValid(rs.getBoolean("valid"));
        return offer;
    }
}
