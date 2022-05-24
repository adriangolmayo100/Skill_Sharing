package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.RowMappers.MaxIdMapper;
import es.uji.ei1027.SkillSharing.RowMappers.SkillTypeRowMapper;
import es.uji.ei1027.SkillSharing.RowMappers.SkillTypeStatisticsRowMapper;
import es.uji.ei1027.SkillSharing.model.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository // En Spring els DAOs van anotats amb @Repository
public class SkillTypeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSkillType(SkillType skillType){
        skillType.setIdSkillType(getNextId());
        jdbcTemplate.update("INSERT INTO SkillType VALUES(?, ?, ?, ?,?)",
                skillType.getIdSkillType(),skillType.getName(),skillType.getDescription(),skillType.getLevel(),skillType.isValid());

    }
    public Integer getNextId(){
        try{
            return jdbcTemplate.queryForObject("SELECT MAX(id_skilltype) AS max_id FROM skilltype",new MaxIdMapper()) + 1;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    public void deleteSkillType(int idSkillType) {
        jdbcTemplate.update("DELETE from SkillType where id_skilltype=?",
                idSkillType );
    }

    public void updateSkillType(SkillType skillType) {
        jdbcTemplate.update("UPDATE SkillType SET name=?, description=?,level=?, valid=?where id_skilltype=?",
                skillType.getName(),skillType.getDescription(),skillType.getLevel(),skillType.isValid(),skillType.getIdSkillType());
    }

    public SkillType getSkillType(int idskilltype) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from SkillType WHERE id_skilltype=?",
                    new SkillTypeRowMapper(), idskilltype);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<SkillType> getSkillTypeWithStatistics() {
        try {
            return jdbcTemplate.query("SELECT st.id_skilltype, st.name, st.description, st.level, st.valid as validSkillType, " +
                            "(SELECT COUNT(off.id_skilltype) " +
                            "FROM offer as off " +
                            "WHERE off.id_skilltype=st.id_skilltype and off.valid=?) AS number_offers, " +
                            "(SELECT COUNT(re.id_skilltype)" +
                            "FROM request as re " +
                            "WHERE re.id_skilltype=st.id_skilltype  and re.valid=?) AS number_requests " +
                            "from SkillType as st " +
                            "WHERE id_skilltype=st.id_skilltype",
                    new SkillTypeStatisticsRowMapper(),true,true);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<SkillType>();
        }
    }

    public List<SkillType> getSkillTypesValid() {
        try {
            return jdbcTemplate.query("SELECT * " +
                            "from SkillType " +
                            "WHERE valid = ?",
                    new SkillTypeRowMapper(),true);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<SkillType>();
        }
    }
    public List<SkillType> getSkillTypes() {
        try {
            return jdbcTemplate.query("SELECT * from SkillType",
                    new SkillTypeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<SkillType>();
        }
    }

}
