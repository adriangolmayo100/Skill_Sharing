package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.User;

import java.util.Collection;

public interface UserDao {
    User loadUserByUsername(String username, String password);
    Collection<User> listAllUsers();
}
