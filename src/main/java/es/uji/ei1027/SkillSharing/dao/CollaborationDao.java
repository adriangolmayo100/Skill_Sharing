package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Collaboration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class CollaborationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCollaboration(Collaboration collaboration) {
        jdbcTemplate.update("INSERT INTO Collaboration VALUES(?, ?, ?, ?,?,?,?,?)",
                collaboration.getIdRequest(), collaboration.getIdOffer(),collaboration.getRating(),collaboration.getStart(),collaboration.getFinish(),collaboration.getDuration(),collaboration.getComments(),collaboration.isValid());
    }

    public void deleteCollaboration(int idrequest, int id_offer) {
        jdbcTemplate.update("DELETE from Collaboration " +
                        "where id_request=? and id_offer=?",
                idrequest,id_offer);
    }

    public void deleteCollaboration(Collaboration collaboration) {
        jdbcTemplate.update("DELETE from Collaboration " +
                        "where id_request=? AND id_offer=?",
                collaboration.getIdRequest(),collaboration.getIdOffer());
    }

    public void updateCollaboration(Collaboration collaboration) {
        jdbcTemplate.update("UPDATE Collaboration SET rating=?,comments=?,start=?,finish=?,duration=?,valid=? WHERE id_request=? and id_offer=?",
                collaboration.getRating(),collaboration.getComments(),collaboration.getStart(),collaboration.getFinish(),collaboration.getDuration(),collaboration.isValid(),collaboration.getIdRequest(),collaboration.getIdOffer());
    }

    public Collaboration getCollaboration(int idRequest, int id_offer) {
        try {
            return jdbcTemplate.queryForObject("SELECT * " +
                            "from Collaboration " +
                            "WHERE id_request=? AND id_offer=?",
                    new CollaborationStandarRowMapper(), idRequest,id_offer);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Collaboration> getCollaborations() {
        try {
            return jdbcTemplate.query("SELECT * " +
                            "from Collaboration " +
                            "JOIN offer USING(id_offer)" +
                            "and valid=?",
                    new CollaborationRowMapper(),true);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
    public List<Collaboration> getRequestCollaborations() {
        try {
            return jdbcTemplate.query("SELECT * " +
                            "from Collaboration " +
                            "JOIN offer USING(id_offer)" +
                            "and valid=?",
                    new CollaborationRowMapper(),false);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
    public List<Collaboration> getMyCollaborationsWhenOffer(int idStudent) {
        try {
            return jdbcTemplate.query("SELECT co.id_request, co.id_offer, co.start," +
                            "co.finish,co.duration, co.rating, of.id_skilltype,co.comments, " +
                            "of.id_student as id_offer_student, re.description," +
                            "re.id_student as id_request_student, co.valid " +
                            "from Collaboration as co " +
                            "JOIN request AS re USING(id_request) " +
                            "JOIN offer AS of USING(id_offer) " +
                            "WHERE of.id_student=?" +
                            "and co.valid=?",
                    new CollaborationRowMapper(),idStudent,true);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
    public List<Collaboration> getMyRequestCollaborationsWhenOffer(int idStudent) {
        try {
            return jdbcTemplate.query("SELECT co.id_request, co.id_offer, co.start," +
                            "co.finish,co.duration, co.rating, of.id_skilltype,co.comments, " +
                            "of.id_student as id_offer_student, re.description," +
                            "re.id_student as id_request_student, co.valid  " +
                            "from Collaboration as co " +
                            "JOIN request AS re USING(id_request) " +
                            "JOIN offer AS of USING(id_offer) " +
                            "WHERE of.id_student=?" +
                            "and co.valid=?",
                    new CollaborationRowMapper(),idStudent,false);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
    public List<Collaboration> getMyCollaborationsWhenRequest(int idStudent) {
        try {
            return jdbcTemplate.query("SELECT co.id_request, co.id_offer, co.start," +
                            "co.finish,co.duration, co.rating, of.id_skilltype,co.comments, " +
                            "of.id_student as id_offer_student, re.description," +
                            "re.id_student as id_request_student, co.valid  " +
                            "from Collaboration as co " +
                            "JOIN request AS re USING(id_request) " +
                            "JOIN offer AS of USING(id_offer) " +
                            "WHERE re.id_student=?" +
                            "and co.valid=?",
                    new CollaborationRowMapper(),idStudent,true);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
    public List<Collaboration> getMyRequestCollaborationsWhenRequest(int idStudent) {
            try {
                return jdbcTemplate.query("SELECT co.id_request, co.id_offer, co.start," +
                                "co.finish,co.duration, co.rating, of.id_skilltype,co.comments, " +
                                "of.id_student as id_offer_student, re.description," +
                                "re.id_student as id_request_student, co.valid " +
                                "from Collaboration as co " +
                                "JOIN request AS re USING(id_request) " +
                                "JOIN offer AS of USING(id_offer) " +
                                "WHERE re.id_student=?" +
                                "and co.valid=?",
                        new CollaborationRowMapper(),idStudent,false);
            } catch (EmptyResultDataAccessException e) {
                return new ArrayList<Collaboration>();
            }
        }
}
