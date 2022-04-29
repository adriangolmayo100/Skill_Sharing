package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRequest(Request request) {
        List<Request>l=getRequests();
        int id = l.size()+1;
        jdbcTemplate.update("INSERT INTO Request VALUES(?, ?, ?, ?,?,?,?)",
                id, request.getDescription(), request.getStart(), request.getFinish(),request.getDuration(),request.getIdStudent(),request.getIdSkillType());
    }

    public void deleteRequest(int idrequest) {
        jdbcTemplate.update("DELETE from Request where id_request=?",
                idrequest );
    }

    public void deleteRequest(Request request) {
        jdbcTemplate.update("DELETE from Request where id_request=?",
                request.getIdRequest());
    }

    public void updateRequest(Request request) {
        jdbcTemplate.update("UPDATE Request SET description=?, start=?,finish=?,duration=?,id_skill_type=? where nom_nadador=? and nom_prova=?",
                request.getDescription(), request.getStart(), request.getFinish(), request.getDuration(),request.getIdSkillType(),request.getIdRequest());
    }

    public Request getRequest(int idRequest) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Request WHERE id_request=?",
                    new RequestRowMapper(), idRequest);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getRequests() {
        try {
            return jdbcTemplate.query("SELECT * from Request",
                    new RequestRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

}
