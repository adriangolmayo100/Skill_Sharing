package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Collaboration;
import es.uji.ei1027.SkillSharing.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class CollaborationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addCollaboartion(Collaboration collaboration) {
        jdbcTemplate.update("INSERT INTO Collaboration VALUES(?, ?, ?, ?, ?, ?)",
                collaboration.getIdRequest(), collaboration.getId_offer(), collaboration.getStart(), collaboration.getFinish(),collaboration.getRating(),collaboration.getComments());
    }

    public void deleteCollaboration(int idrequest, int id_offer) {
        jdbcTemplate.update("DELETE from Collaboration where id_request=?",
                idrequest,id_offer);
    }

    public void deleteCollaboration(Collaboration collaboration) {
        jdbcTemplate.update("DELETE from Collaboration where id_request=? AND id_offer=?",
                collaboration.getIdRequest(),collaboration.getId_offer());
    }

    public void updateCollaboration(Collaboration collaboration) {
        jdbcTemplate.update("UPDATE Collaboration SET start=?,finish=?,rating=?,comments=? ",
                collaboration.getStart(),collaboration.getFinish(),collaboration.getRating(),collaboration.getComments());
    }

    public Collaboration getCollaboration(int idRequest, int id_offer) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Collaboration WHERE id_request=? AND id_offer=?",
                    new CollaborationRowMapper(), idRequest,id_offer);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Collaboration> getCollaboration() {
        try {
            return jdbcTemplate.query("SELECT * from Collaboration",
                    new CollaborationRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Collaboration>();
        }
    }
}
