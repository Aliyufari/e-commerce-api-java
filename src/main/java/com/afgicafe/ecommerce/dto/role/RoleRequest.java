package com.afgicafe.ecommerce.dto.role;

import com.afgicafe.ecommerce.entity.Permission;

import java.util.Set;
import java.util.UUID;

public record RoleRequest(
        String name,
        Set<UUID> permissionIds
) {}
