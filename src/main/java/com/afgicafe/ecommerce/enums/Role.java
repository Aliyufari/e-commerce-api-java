package com.afgicafe.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(
            List.of(
                    Permission.USER_VIEW,
                    Permission.USER_CREATE,
                    Permission.USER_DELETE,
                    Permission.USER_UPDATE
            )
    ),

    SALE(List.of()),

    CUSTOMER(List.of());

    private final List<Permission> permissions;
}
