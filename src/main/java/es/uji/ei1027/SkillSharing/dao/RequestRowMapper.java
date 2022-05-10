package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Request;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public final class RequestRowMapper implements RowMapper<Request> {

    public Request mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Request request = new Request();
        request.setIdRequest(rs.getInt("id_request"));
        request.setDescription(rs.getString("description"));
        request.setDuration(rs.getInt("duration"));
        request.setStart(rs.getObject("start", LocalDate.class));
        request.setFinish(rs.getObject("finish", LocalDate.class));
        request.setIdSkillType(rs.getInt("id_skilltype"));
        request.setIdStudent(rs.getInt("id_student"));
        request.setValid(rs.getBoolean("valid"));
        return request;
    }

}
