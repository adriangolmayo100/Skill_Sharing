package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class SkillTypeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSkillType(SkillType skillType){
        jdbcTemplate.update("INSERT INTO SkillType VALUES(?, ?, ?, ?)",
                skillType.getIdskillType(),skillType.getName(),skillType.getDescription(),skillType.getLevel());

    }
    public void deleteSkillType(int idSkillType) {
        jdbcTemplate.update("DELETE from SkillType where id_skilltype=?",
                idSkillType );
    }

    public void deleteRequest(SkillType skillType) {
        jdbcTemplate.update("DELETE from SkillType where id_request=?",
                skillType.getIdskillType());
    }

    public void updateRequest(SkillType skillType) {
        jdbcTemplate.update("UPDATE SkillType SET name=?, description=?,level=? where id_skilltype=?",
                skillType.getName(),skillType.getDescription(),skillType.getLevel(),skillType.getIdskillType());
    }

    public SkillType getRequest(int idskilltype) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from SkillType WHERE id_skilltype=?",
                    new SkillTypeRowMapper(), idskilltype);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<SkillType> getRequests() {
        try {
            return jdbcTemplate.query("SELECT * from SkillType",
                    new SkillTypeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<SkillType>();
        }
    }
}
