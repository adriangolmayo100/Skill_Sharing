package es.uji.ei1027.SkillSharing.dao;

import es.uji.ei1027.SkillSharing.model.User;

import java.util.Collection;

public interface UserInt {
    User loadUserByUsername(String username, String password);
    Collection<User> listAllUsers();
}
