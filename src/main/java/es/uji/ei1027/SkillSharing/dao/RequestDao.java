package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.RowMappers.MaxIdMapper;
import es.uji.ei1027.SkillSharing.RowMappers.RequestRowMapper;
import es.uji.ei1027.SkillSharing.RowMappers.StudentRowMapper;
import es.uji.ei1027.SkillSharing.model.Request;
import es.uji.ei1027.SkillSharing.model.Student;
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
    public Integer getNextId(){
        try{
            return jdbcTemplate.queryForObject("SELECT MAX(id_request) AS max_id FROM request",new MaxIdMapper()) + 1;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    public void addRequest(Request request) {
        request.setIdRequest(getNextId());
        jdbcTemplate.update("INSERT INTO Request VALUES(?, ?, ?, ?,?,?,?,?)",
                request.getIdRequest(),request.getIdStudent(),request.getIdSkillType(),request.getDescription(),request.getStart(), request.getFinish(),request.getDuration(),request.isValid());
    }

    public void deleteRequest(int idrequest) {
        jdbcTemplate.update("UPDATE request SET finish=CURRENT_DATE-1, valid=false where id_request=?",
                idrequest );
    }

    public void deleteRequest(Request request) {
        jdbcTemplate.update("UPDATE request SET finish=CURRENT_DATE-1 WHERE id_request=?",
                request.getIdRequest());
    }

    public void updateRequest(Request request) {
        jdbcTemplate.update("UPDATE Request SET description=?, start=?,finish=?,duration=?,id_skilltype=?, valid=? where id_request=?",
                request.getDescription(), request.getStart(), request.getFinish(), request.getDuration(),request.getIdSkillType(),request.isValid(),request.getIdRequest());
    }

    public Request getRequest(int idRequest) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Request WHERE id_request=?",
                    new RequestRowMapper(), idRequest);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Request> getValidRequests() {
        try {
            return jdbcTemplate.query("SELECT * " +
                            "from Request" +
                            " WHERE valid=?" +
                            "and finish>CURRENT_DATE",
                    new RequestRowMapper(), true);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Student> getStudentsWithIdSkillType(int idSkilltypes, int idStudent) {
        try {
            return jdbcTemplate.query("SELECT st.* " +
                    "from Request as re " +
                    "JOIN student as st USING(id_student)\n" +
                    "WHERE valid=?" +
                    "and re.id_skilltype=?" +
                    "and st.id_student!=?;" ,new StudentRowMapper(), true,idSkilltypes,idStudent);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Student>();
        }
    }
    public List<Request> getValidRequests(int idSkillType, int idStudent) {
        try {
            return jdbcTemplate.query("SELECT * " +
                            "from Request " +
                            "WHERE valid=? " +
                            "and id_skilltype=? " +
                            "and id_student!=? " +
                            "and finish>CURRENT_DATE",
                    new RequestRowMapper(), true, idSkillType,idStudent);
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

    public List<Request> getRequests(int idUser) {
        try {
            return jdbcTemplate.query("SELECT * from Request WHERE valid=? and id_student=?",
                    new RequestRowMapper(),true, idUser);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

}
