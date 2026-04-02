package com.afgicafe.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoleResponse(
        UUID id,

        String name,

        Set<PermissionResponse> permissions,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("created_by")
        UUID createdBy,

        @JsonProperty("updated_by")
        UUID updatedBy,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {}
