package com.afgicafe.ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "permissions")
@NoArgsConstructor
@SuperBuilder
public class Permission extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String name;

    public Permission(UUID id, String name) {
        this.setId(id);
        this.name = name;
    }
}
