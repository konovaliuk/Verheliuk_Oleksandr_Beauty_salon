package org.webproject.dao;

import org.webproject.models.Role;

import java.util.List;

public interface RoleDAO extends DAO<Role>{

    List<Role> getUserRoles(long idUser);

    void deleteUserRoles(long idUser);


}
