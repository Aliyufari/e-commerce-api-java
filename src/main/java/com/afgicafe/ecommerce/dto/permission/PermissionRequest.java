package com.afgicafe.ecommerce.dto.permission;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequest(
      @NotBlank(message = "Name field is required")
      String name
) {}
