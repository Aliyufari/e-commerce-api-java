package com.afgicafe.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoleSimpleResponse(
        UUID id,
        String name,
        List<PermissionSimpleResponse> permissions
) {}
