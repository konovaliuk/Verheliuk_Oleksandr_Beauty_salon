package org.webproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.webproject.models.Role;
import org.webproject.models.User;

public interface RoleRepository extends JpaRepository<Role, Long>{
    @Query("select r from Role r where r.roleType = ?1")
    Role findByName(String name);
}
