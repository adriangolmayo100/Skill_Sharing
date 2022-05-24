package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.RowMappers.StatisticCollaborationRowMapper;
import es.uji.ei1027.SkillSharing.RowMappers.StatisticOffersRequestsRowMapper;
import es.uji.ei1027.SkillSharing.model.StatisticStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public StatisticStudent getStatisticCollaborationsFromStudent(int id_student){
        try{
            return jdbcTemplate.queryForObject("SELECT st.id_student, \n" +
                    "\t(CASE WHEN (AVG( (CASE WHEN (co.rating<1) THEN NULL ELSE co.rating END)) IS NULL) THEN 0 ELSE AVG((CASE WHEN (co.rating<1) THEN NULL ELSE co.rating END)) END) as mean_rating, \n" +
                    "\tCOUNT(CASE WHEN (co.rating>0) THEN 1 ELSE NULL END) as collaboration_finished\n" +
                    "                          from student as st\n" +
                    "                          LEFT JOIN offer AS of USING(id_student)\n" +
                    "                          LEFT JOIN collaboration as co USING(id_offer)\n" +
                    "                          WHERE st.id_student=?\n" +
                    "                          GROUP BY st.id_student",new StatisticCollaborationRowMapper(),id_student);
        }catch (EmptyResultDataAccessException e){
            return new StatisticStudent(id_student);
        }
    }
    public List<StatisticStudent> getStatisticCollaborations(){
        try{
            return jdbcTemplate.query("SELECT st.id_student, \n" +
                    "\t(CASE WHEN (AVG( (CASE WHEN (co.rating<1) THEN NULL ELSE co.rating END)) IS NULL) THEN 0 ELSE AVG((CASE WHEN (co.rating<1) THEN NULL ELSE co.rating END)) END) as mean_rating, \n" +
                    "\tCOUNT(CASE WHEN (co.rating>0) THEN 1 ELSE NULL END) as collaboration_finished\n" +
                    "                          from student as st\n" +
                    "                          LEFT JOIN offer AS of USING(id_student)\n" +
                    "                          LEFT JOIN collaboration as co USING(id_offer)\n" +
                    "                          GROUP BY st.id_student",new StatisticCollaborationRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
     public StatisticStudent getStatisticOffersRequestFromStudent(int id_student){
        try{
            return jdbcTemplate.queryForObject("SELECT id_student, COUNT(DISTINCT of.id_offer) as num_ofertas, COUNT(DISTINCT re.id_request) as num_request\n" +
                    "from student as st\n" +
                    "JOIN offer AS of USING(id_student)\n" +
                    "LEFT JOIN request AS re USING(id_student)\n" +
                    "WHERE st.id_student=?" +
                    "GROUP BY st.id_student" ,new StatisticOffersRequestsRowMapper(),id_student);
        }catch (EmptyResultDataAccessException e){
            return new StatisticStudent(id_student);
        }
    }
    public List<StatisticStudent> getStatisticOffersRequest(){
        try{
            return jdbcTemplate.query("SELECT st.id_student, COUNT(DISTINCT of.id_offer) as num_ofertas, COUNT(DISTINCT re.id_request) as num_request\n" +
                    "        from student as st\n" +
                    "        LEFT JOIN offer AS of USING(id_student)\n" +
                    "        LEFT JOIN request AS re USING(id_student)\n" +
                    "        GROUP BY st.id_student",new StatisticOffersRequestsRowMapper());
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }
}
