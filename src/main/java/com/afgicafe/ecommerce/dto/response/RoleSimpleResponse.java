package com.afgicafe.ecommerce.dto.response;

import java.util.List;
import java.util.UUID;

public record RoleSimpleResponse(
        UUID id,
        String name,
        List<PermissionSimpleResponse> permissions
) {}
