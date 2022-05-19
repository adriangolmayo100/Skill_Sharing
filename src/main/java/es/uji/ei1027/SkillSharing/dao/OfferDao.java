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
        offer.setIdOffer(getNextId());
        jdbcTemplate.update("INSERT INTO Offer Values(?, ?, ?, ?, ?, ?, ?,?)",
                offer.getIdOffer(), offer.getIdStudent(), offer.getIdSkillType(), offer.getDescription(),
                offer.getStart(), offer.getFinish(), offer.getDuration(), offer.isValid());
    }
    public Integer getNextId(){
        try{
            return jdbcTemplate.queryForObject("SELECT MAX(id_offer) AS max_id FROM offer",new MaxIdMapper()) + 1;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    public void deleteOffer(Integer idOffer){
        jdbcTemplate.update("UPDATE offer SET finish=CURRENT_DATE-1, valid=false WHERE id_offer=?", idOffer);
    }

    public void deleteOffer(Offer offer){
        jdbcTemplate.update("DELETE FROM offer where id_offer=?", offer.getIdOffer());
    }

    public void updateOffer(Offer offer){
        jdbcTemplate.update("UPDATE offer SET id_student=?, id_skilltype=?, description=?, start=?," +
                "finish=?, duration=?,valid=? where id_offer=?",
                offer.getIdStudent(), offer.getIdSkillType(), offer.getDescription(),
                offer.getStart(), offer.getFinish(), offer.getDuration(),offer.isValid(), offer.getIdOffer());
    }

    public Offer getOffer(int idOffer){
        try{
            return jdbcTemplate.queryForObject("Select * from offer where id_offer=?",
                    new OfferRowMapper(), idOffer);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    public List<Offer> getValidOffers() {
        try {
            return jdbcTemplate.query("SELECT * from offer WHERE valid=?",
                    new OfferRowMapper(),true);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Offer> getValidOffers(Request request) {
        try {
            return jdbcTemplate.query("SELECT * \n" +
                            "from offer \n" +
                            "WHERE valid=?\n" +
                            "and id_skilltype=? \n" +
                            "and id_student!=?\n" +
                            "and NOT EXISTS\n" +
                            "(SELECT id_offer\n" +
                            "FROM collaboration\n" +
                            "WHERE id_request=?)",
                    new OfferRowMapper(),true,request.getIdSkillType(), request.getIdStudent(), request.getIdRequest());
        } catch (EmptyResultDataAccessException e) {
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

    public List<Offer> getOffers(int idUser){
        try{
            return jdbcTemplate.query("SELECT * FROM offer WHERE valid=? and id_student=?",
                    new OfferRowMapper(),true,idUser);
        }catch(EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}
