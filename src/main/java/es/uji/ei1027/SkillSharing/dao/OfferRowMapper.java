package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Offer;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public final class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setIdOffer(rs.getInt("id_offer"));
        offer.setIdStudent(rs.getInt("id_student"));
        offer.setIdSkillType(rs.getInt("id_skilltype"));
        offer.setDescription(rs.getString("description"));
        Time start = rs.getTime("start");
        offer.setStart(start != null ? start.toLocalTime() : null);
        Time finish = rs.getTime("finish");
        offer.setStart(finish != null ? finish.toLocalTime() : null);
        offer.setDuration(rs.getInt("duration"));
        return offer;
    }
}
