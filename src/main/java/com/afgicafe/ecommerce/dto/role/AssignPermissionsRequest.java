package com.afgicafe.ecommerce.dto.role;

import java.util.Set;
import java.util.UUID;

public record AssignPermissionsRequest(
        Set<UUID> permissionIds
) {}
