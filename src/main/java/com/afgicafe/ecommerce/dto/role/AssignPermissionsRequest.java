package com.afgicafe.ecommerce.dto.role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record AssignPermissionsRequest(
        @NotEmpty(message = "Permissions must not be empty")
        Set<@NotNull(message = "Permission ID cannot be null") UUID> permissionIds
) {}
