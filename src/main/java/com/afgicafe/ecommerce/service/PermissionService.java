package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.permission.PermissionRequest;
import com.afgicafe.ecommerce.entity.Permission;

import java.util.Set;

public interface PermissionService {
    Set<Permission> getPermissions();
    Permission createPermission(PermissionRequest request);
}
