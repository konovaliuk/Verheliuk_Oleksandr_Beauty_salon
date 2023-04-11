package org.webproject.dao;

import org.webproject.models.Role;

import java.util.List;

public interface RoleDAO extends DAO<Role>{

    Role getByName(String name);

    List<Role> getUserRoles(long idUser);

    void deleteUserRoles(long idUser);

}