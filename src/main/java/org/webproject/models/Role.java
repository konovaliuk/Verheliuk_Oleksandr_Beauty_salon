package org.webproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Role {

    private long roleId;

    private String role_type;

}
