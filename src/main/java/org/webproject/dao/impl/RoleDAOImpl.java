package org.webproject.dao.impl;

import jakarta.persistence.EntityManager;
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

    private final EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);
    public RoleDAOImpl(EntityManager em){
        this.em = em;
    }

    @Override
    public Role get(long id) {
        return em.find(Role.class, id);
    }

    @Override
    public Role getByName(String name){
        return em.createQuery("SELECT r FROM Role r WHERE r.roleType = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Role> getAll() {
        return em.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role create(Role role) {
        em.persist(role);
        return role;
    }

    @Override
    public void update(Role role) {
        em.merge(role);
    }

    @Override
    public void delete(long id) {
        em.remove(em.find(Role.class, id));
    }

//    @Override
//    public List<Role> getUserRoles(long idUser){
//        try (PreparedStatement stmt = con.prepareStatement(SQL_SELECT_USER_ROLES)){
//            List<Role> userRoles = new ArrayList<>();
//            stmt.setLong(1, idUser);
//            try (ResultSet rs = stmt.executeQuery()){
//                while (rs.next()){
//                    Role role = getRole(rs);
//                    userRoles.add(role);
//                }
//                return userRoles;
//            }
//        } catch (SQLException ex) {
//            logger.error("Error. Can't find roles for user with id = " + idUser + ". " + ex.getMessage());
//        }
//        return null;
//    }
//
//    public void deleteUserRoles(long idUser){
//        try (PreparedStatement stmt = con.prepareStatement(SQL_DELETE_USER_ROLES)){
//            stmt.setLong(1, idUser);
//            stmt.executeUpdate();
//        } catch (SQLException ex) {
//            logger.error("Error. Can't delete roles for user with id = " + idUser + ". " + ex.getMessage());
//        }
//    }
}
