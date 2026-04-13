package com.afgicafe.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PermissionSimpleResponse(
        UUID id,
        String name
) {}
