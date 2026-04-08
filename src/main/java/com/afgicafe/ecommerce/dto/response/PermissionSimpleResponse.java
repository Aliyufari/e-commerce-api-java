package com.afgicafe.ecommerce.dto.response;

import java.util.UUID;

public record PermissionSimpleResponse(
        UUID id,
        String name
) {}
