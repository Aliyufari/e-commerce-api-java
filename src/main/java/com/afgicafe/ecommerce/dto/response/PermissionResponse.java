package com.afgicafe.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PermissionResponse(
        UUID id,

        String name,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("created_by")
        UUID createdBy,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt,

        @JsonProperty("updated_by")
        UUID updatedBy
) {}
