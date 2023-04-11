package org.webproject.dao;

import org.webproject.dao.DAO;
import org.webproject.models.Role;
import org.webproject.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UserDAO extends DAO<User>{

    public User getByEmail(String email);

    public User addUserRole(User user, Role role);
}