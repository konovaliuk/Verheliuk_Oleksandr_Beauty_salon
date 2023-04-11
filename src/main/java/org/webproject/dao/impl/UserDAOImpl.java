package org.webproject.dao.impl;

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

    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SQL_ADD_USER = "INSERT INTO users (email, `password`, first_name, " +
            "last_name) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET email = ?, `password` = ?, first_name = ?, " +
            "last_name = ? WHERE id = ?";
    private static final String SQL_ADD_USER_ROLE = "INSERT INTO user_role (id_user, id_role) VALUES (?, ?)";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public UserDAOImpl(Connection con){
        this.con = con;
    }
    @Override
    public User get(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = getUser(rs);
                    List<Role> userRoles = new RoleDAOImpl(con).getUserRoles(user.getUserId());
                    user.setRoles(userRoles);
                    return user;
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find User by id " + id + ". " + ex.getMessage());
        }
        return null;
    }

    private User getUser(ResultSet rs){
        try {
            long id = rs.getLong("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            List<Role> roles = new RoleDAOImpl(con).getUserRoles(id);
            return new User(id, email, password, firstName, lastName, roles);
        }
        catch (SQLException ex) {
            logger.error("Error. Can't get User." + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_USERS)){
            List<User> users = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = getUser(rs);
                    users.add(user);
                }
                return users;
            }
        }
        catch (SQLException ex) {
            logger.error("Error. Can't get all users." + ex.getMessage());
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_BY_EMAIL)){
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = getUser(rs);
                    List<Role> userRoles = new RoleDAOImpl(con).getUserRoles(user.getUserId());
                    user.setRoles(userRoles);
                    return user;
                }
            }
        }
        catch (SQLException ex) {
            logger.error("Error. Can't find user by email " + email + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public User create(User user) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_USER, PreparedStatement.RETURN_GENERATED_KEYS)){
            setUser(stmt, user);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getLong(1));
                }
            }
        } catch (SQLException ex){
            logger.error("Error. Can't create new User: " + ex.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_USER)){
            stmt.setLong(5, user.getUserId());
            setUser(stmt, user);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }

    @Override
    public User addUserRole(User user, Role role){
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_USER_ROLE)){
            stmt.setLong(1, user.getUserId());
            stmt.setLong(2, role.getRoleId());
            stmt.executeUpdate();
            user.getRoles().add(role);
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
        return user;
    }

    private void setUser(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getFirstName());
        stmt.setString(4, user.getLastName());
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_USER)){
            new RoleDAOImpl(con).deleteUserRoles(id);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error: " + ex.getMessage());
        }
    }
}
