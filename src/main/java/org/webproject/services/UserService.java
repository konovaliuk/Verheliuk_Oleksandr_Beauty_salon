package org.webproject.services;

import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webproject.models.User;

import org.slf4j.Logger;
import org.webproject.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;


    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(long id){
        return userRepository.findById(id).orElse(null);
    };
    public List<User> getAllUsers(){
        return userRepository.findAll();
    };
    public User loginUser(String login, String password) {
        User user = userRepository.findByMail(login);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    public User registerUser(User user) {
        user.addRole(roleService.getRoleByName("user"));
        if (userRepository.findByMail(user.getEmail()) != null) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public void updateUser(User user){
        userRepository.save(user);
    }
    public void deleteUser(long id){
        userRepository.deleteById(id);
    }
   }
