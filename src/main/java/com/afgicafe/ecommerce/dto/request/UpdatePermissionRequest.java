package com.afgicafe.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UniqueElements;

public record UpdatePermissionRequest(
        @NotBlank(message = "Name field is required")
        @UniqueElements
        String name
) {}
