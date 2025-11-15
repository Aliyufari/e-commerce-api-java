package com.afgicafe.ecommerce.entity;

import com.afgicafe.ecommerce.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dob;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
