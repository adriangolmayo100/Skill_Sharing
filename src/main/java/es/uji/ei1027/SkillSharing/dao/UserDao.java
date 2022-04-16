package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.User;
import es.uji.ei1027.SkillSharing.dao.UserPasswd;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    UserPasswd userPasswd = new UserPasswd(); //Clase para acceder al fichero de usuarios

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User cargaUsuario(String username, String password) throws FileNotFoundException {   //Busca el usuario, y si existe lo devuelve como objeto User. NULL si no existe
        UserPasswd userPasswd = new UserPasswd();
        if(userPasswd.encontrarUser(username) && userPasswd.comprobarPasswd(password)){
            return new User(username, password);
        }return null;
    }

    public void nuevoUsuario(String username, String password) throws IOException {
        User usuario = cargaUsuario(username, password);
        if (usuario == null && username.length() > 0 && password.length() > 0){
            userPasswd.nuevoUser(username, password);
        }
    }

    public String obtenerId() throws IOException { //Sirve para obtener ID, no llamo a la clase UserPasswd desde el controller para respetar la jerarquía
        return userPasswd.contarUsuarios();
    }

    public void registrar(){}

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


