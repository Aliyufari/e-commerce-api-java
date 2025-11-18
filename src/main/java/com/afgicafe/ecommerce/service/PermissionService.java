package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.permission.PermissionRequest;
import com.afgicafe.ecommerce.entity.Permission;

import java.util.Set;
import java.util.UUID;

public interface PermissionService {
    Set<Permission> getPermissions();
    Permission createPermission(PermissionRequest request);
    Permission getPermission(UUID id);
}
