package com.afgicafe.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(
            List.of(
                    PermissionEnum.USER_VIEW,
                    PermissionEnum.USER_CREATE,
                    PermissionEnum.USER_DELETE,
                    PermissionEnum.USER_UPDATE
            )
    ),

    SALE(List.of()),

    CUSTOMER(List.of());

    private final List<PermissionEnum> permissionEnums;
}
