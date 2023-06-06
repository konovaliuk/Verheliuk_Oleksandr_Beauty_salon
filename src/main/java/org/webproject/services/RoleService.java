package org.webproject.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webproject.models.Role;
import org.webproject.repository.RoleRepository;

import java.util.List;
@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role getRole(long id) {
        return  roleRepository.findById(id).orElse(null);
    }
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    public Role createRole(Role role){
        return roleRepository.save(role);
    }
    public void updateRole(Role role){
        roleRepository.save(role);

    }
    public void deleteRole(long id){
        roleRepository.deleteById(id);
    }
}
