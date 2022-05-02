package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Offer;
import es.uji.ei1027.SkillSharing.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class OfferDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) { jdbcTemplate = new JdbcTemplate(dataSource); }

    public void addOffer(Offer offer){
        List<Offer>l=getOffers();
        offer.setIdOffer(l.size()+1);
        jdbcTemplate.update("INSERT INTO Offer Values(?, ?, ?, ?, ?, ?, ?)",
                offer.getIdOffer(), offer.getIdStudent(), offer.getIdSkillType(), offer.getDescription(),
                offer.getStart(), offer.getFinish(), offer.getDuration());
    }

    public void deleteOffer(Integer idOffer){
        jdbcTemplate.update("DELETE FROM Offer where id_offer=?", idOffer);
    }

    public void deleteOffer(Offer offer){
        jdbcTemplate.update("DELETE FROM offer where id_offer=?", offer.getIdOffer());
    }

    public void updateOffer(Offer offer){
        jdbcTemplate.update("UPDATE offer SET id_student=?, id_skilltype=?, description=?, start=?," +
                "finish=?, duration=? where id_offer=?",
                offer.getIdStudent(), offer.getIdSkillType(), offer.getDescription(),
                offer.getStart(), offer.getFinish(), offer.getDuration(), offer.getIdOffer());
    }

    public Offer getOffer(Integer idOffer){
        try{
            return jdbcTemplate.queryForObject("Select * from offer where id_offer=?",
                    new OfferRowMapper(), idOffer);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    public List<Offer> getOffers(){
        try{
            return jdbcTemplate.query("SELECT * FROM offer",
                    new OfferRowMapper());
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}
