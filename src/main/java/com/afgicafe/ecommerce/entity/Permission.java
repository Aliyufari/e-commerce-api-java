package com.afgicafe.ecommerce.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Permission extends BaseEntity{
    private String name;
}
