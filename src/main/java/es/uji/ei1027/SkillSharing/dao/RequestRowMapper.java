package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.Request;
import org.springframework.jdbc.core.RowMapper;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


public final class RequestRowMapper implements RowMapper<Request> {

    public Request mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Request request = new Request();
        request.setIdRequest(rs.getInt("idrequest"));
        request.setDescription(rs.getString("description"));
        request.setDuration(rs.getInt("duration"));
        Date d = rs.getDate("start");
        request.setStart(d);
        Date d1 = rs.getDate("finish");
        request.setFinish(d1);
        request.setIdSkillType(rs.getInt("idskilltype"));
        request.setIdStudent(rs.getInt("idstudent"));

        return request;
    }

}