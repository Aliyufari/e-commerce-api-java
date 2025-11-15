package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.role.AssignPermissionsRequest;
import com.afgicafe.ecommerce.dto.role.RoleRequest;
import com.afgicafe.ecommerce.entity.Role;

import java.util.Set;
import java.util.UUID;

public interface RoleService {
    Set<Role> getRoles();
    Role createRole(RoleRequest request);
    Role getRole(UUID id);
    Role updateRole(RoleRequest request, UUID id);
    void deleteRole(UUID id);
    Role assignPermissionsToRole(AssignPermissionsRequest request, UUID id);
}
