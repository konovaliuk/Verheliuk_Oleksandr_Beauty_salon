package org.webproject.services;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.LoggerFactory;
import org.webproject.dao.ConnectionPool;
import org.webproject.dao.RoleDAO;
import org.webproject.dao.UserDAO;
import org.webproject.dao.impl.RoleDAOImpl;
import org.webproject.dao.impl.UserDAOImpl;
import org.webproject.models.Role;
import org.webproject.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.util.List;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    public User getUser(long id){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                return userDAO.get(id);
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    };
    public List<User> getAllUsers(){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                return userDAO.getAll();
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    };
    public User loginUser(String login, String password) {
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                User user = userDAO.getByEmail(login);
                if (checkPassword(password, user.getPassword())){
                    return user;
                }
            } catch (Exception ex) {
                logger.error("Error: " + ex.getMessage());
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    public User registerUser(User user) {
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                Role role = roleDAO.getByName("user");
                user.setPassword(getHash(user.getPassword()));
                if (userDAO.create(user) != null) {
                    userDAO.addUserRole(user, role);
                    con.commit();
                    return user;
                }
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
        return null;
    }
    public void updateUser(User user){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                con.setAutoCommit(false);
                userDAO.update(user);
                con.commit();
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
    }
    public void addUserRole(long userId, long roleId){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            RoleDAO roleDAO = new RoleDAOImpl(con);
            try {
                con.setAutoCommit(false);
                User user = userDAO.get(userId);
                Role role = roleDAO.get(roleId);
                if (userDAO.addUserRole(user, role) != null) {
                    con.commit();
                }
            } catch (Exception ex) {
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex){
            logger.error("Error: " + ex.getMessage());
        }
    }
    public boolean deleteUser(long id, String password){
        try (Connection con = ConnectionPool.getConnection()){
            UserDAO userDAO = new UserDAOImpl(con);
            try {
                con.setAutoCommit(false);
                User user = userDAO.get(id);
                if (checkPassword(password, user.getPassword())) {
                    userDAO.delete(id);
                    con.commit();
                    return true;
                }
            } catch (Exception ex){
                try {
                    con.rollback();
                } catch (Exception e){
                    logger.error("Error: " + e.getMessage());
                }
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return false;
    };
    private String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    private boolean checkPassword(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
