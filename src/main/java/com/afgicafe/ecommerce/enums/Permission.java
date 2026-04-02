package com.afgicafe.ecommerce.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    USER_VIEW("user:view"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:create");

    private final String value;

    public static  Permission fromValue (String value){
        for(Permission permission : values()){
            if (permission.value.equals(value))
                return permission;
        }

        throw new IllegalArgumentException("Unknown Permission: " + value);
    }
}
