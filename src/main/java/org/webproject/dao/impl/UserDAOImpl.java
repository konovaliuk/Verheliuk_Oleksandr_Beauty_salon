package org.webproject.dao.impl;

import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;
import org.webproject.dao.UserDAO;
import org.webproject.models.Role;
import org.webproject.models.User;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public UserDAOImpl(EntityManager em){
        this.em = em;
    }
    @Override
    public User get(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getByEmail(String email) {
        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public User create(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

//    @Override
//    public User addUserRole(User user, Role role){
//        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_USER_ROLE)){
//            stmt.setLong(1, user.getUserId());
//            stmt.setLong(2, role.getRoleId());
//            stmt.executeUpdate();
//            user.getRoles().add(role);
//        } catch (SQLException ex) {
//            logger.error("Error: " + ex.getMessage());
//        }
//        return user;
//    }

    @Override
    public void delete(long id) {
        em.remove(em.find(User.class, id));
    }

//    public String getHash(String password) {
//        return BCrypt.hashpw(password, BCrypt.gensalt());
//    }
//
//    public boolean checkPassword(String password, String hash) {
//        return BCrypt.checkpw(password, hash);
//    }
}
