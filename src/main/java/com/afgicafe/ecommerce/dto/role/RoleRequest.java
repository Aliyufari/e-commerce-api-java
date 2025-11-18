package com.afgicafe.ecommerce.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record RoleRequest(
        @NotBlank(message = "Name field is required")
        String name,
        @NotEmpty(message = "Permissions must not be empty")
        Set<@NotNull(message = "Permission ID cannot be null") UUID> permissionIds
) {}
