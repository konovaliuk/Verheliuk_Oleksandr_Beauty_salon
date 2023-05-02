package org.webproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor

public class UserRole {

    @Id
    @Column(name = "id_user")
    private long userId;

    @Column(name = "id_role")
    private long roleId;
}
