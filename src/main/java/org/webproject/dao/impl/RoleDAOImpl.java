package org.webproject.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webproject.dao.RoleDAO;
import org.webproject.models.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements RoleDAO {

    private static final String SQL_SELECT_ROLE_BY_ID = "SELECT * FROM role WHERE id = ?";
    private static final String SQL_SELECT_ROLE_BY_NAME = "SELECT * FROM role WHERE role_type = ?";
    private static final String SQL_SELECT_ALL_ROLES = "SELECT * FROM role";
    private static final String SQL_ADD_ROLE = "INSERT INTO role (role_type) VALUES(?)";
    private static final String SQL_UPDATE_ROLE = "UPDATE role SET role_type WHERE id = ?";
    private static final String SQL_DELETE_ROLE = "DELETE FROM role WHERE id = ?";
    private static final String SQL_DELETE_USERS_ROLE = "DELETE FROM user_role WHERE id_role = ?";
    private static final String SQL_SELECT_USER_ROLES = "SELECT r.id, role_type FROM user_role ur JOIN role r " +
            "ON ur.id_role = r.id WHERE ur.id_user = ?";
    private static final String SQL_DELETE_USER_ROLES = "DELETE FROM user_role WHERE id_user = ?";
    private final Connection con;
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    public RoleDAOImpl(Connection con){
        this.con = con;
    }

    @Override
    public Role get(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ROLE_BY_ID)) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getRole(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find Roel by id " + id + ". " + ex.getMessage());
        }
        return null;
    }

    private Role getRole(ResultSet rs){
        try {
            long id = rs.getLong("id");
            String role_type = rs.getString("role_type");
            return new Role(id, role_type);
        }
        catch (SQLException ex) {
            logger.error("Error. Can't get Role." + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Role> getAll() {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ALL_ROLES)) {
            List<Role> roles = new ArrayList<>();
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Role role = getRole(rs);
                    roles.add(role);
                }

                return roles;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't get all roles."+ ex.getMessage());
        }
        return null;
    }

    @Override
    public Role create(Role role) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_ADD_ROLE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setRole(stmt, role);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    role.setRoleId(rs.getLong(1));
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't add new role " + role.getRole_type() + ". " + ex.getMessage());
        }
        return role;
    }

    private void setRole(PreparedStatement stmt, Role role) throws SQLException{
        stmt.setString(1, role.getRole_type());
    }

    @Override
    public void update(Role role) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_ROLE)) {
            stmt.setLong(2, role.getRoleId());
            setRole(stmt, role);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't update role " + role.getRole_type() + ". " + ex.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_USERS_ROLE)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete role with id " + id + ". " + ex.getMessage());
        }
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_ROLE)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete role with id " + id + ". " + ex.getMessage());
        }
    }

    @Override
    public Role getByName(String name) {
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_ROLE_BY_NAME)) {
            stmt.setString(1, name);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getRole(rs);
                }
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find Roel by name " + name + ". " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Role> getUserRoles(long idUser){
        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_ROLES)){
            List<Role> userRoles = new ArrayList<>();
            stmt.setLong(1, idUser);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Role role = getRole(rs);
                    userRoles.add(role);
                }
                return userRoles;
            }
        } catch (SQLException ex) {
            logger.error("Error. Can't find roles for user with id = " + idUser + ". " + ex.getMessage());
        }
        return null;
    }

    public void deleteUserRoles(long idUser){
        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_USER_ROLES)){
            stmt.setLong(1, idUser);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error("Error. Can't delete roles for user with id = " + idUser + ". " + ex.getMessage());
        }
    }
}
