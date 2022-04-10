package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {
    JdbcTemplate jdbcTemplate;
    final Map<String, User> knownUsers = new HashMap<String, User>();

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User loadUserByUsername(String username, String password) {
        User user = knownUsers.get(username.trim());
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
            return user;
        }
        else {
            return null; // bad login!
        }
    }

    public Collection<User> listAllUsers() {
        return knownUsers.values();
    }
}


