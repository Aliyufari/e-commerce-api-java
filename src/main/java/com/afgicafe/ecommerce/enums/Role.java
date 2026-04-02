package com.afgicafe.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    Permission.USER_VIEW,
                    Permission.USER_CREATE,
                    Permission.USER_DELETE,
                    Permission.USER_UPDATE
            )
    ),

    SALE(Collections.emptySet()),

    CUSTOMER(Collections.emptySet());

    private final Set<Permission> permissions;
}
