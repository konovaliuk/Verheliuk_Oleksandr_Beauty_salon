package org.webproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class User {

    private long userId;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private List<Role> roles;
}