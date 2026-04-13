package com.afgicafe.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionEnum {
    USER_VIEW("user:view"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:create");

    private final String value;

    public static PermissionEnum fromValue (String value){
        for(PermissionEnum permissionEnum : values()){
            if (permissionEnum.value.equals(value))
                return permissionEnum;
        }

        throw new IllegalArgumentException("Unknown Permission: " + value);
    }
}
