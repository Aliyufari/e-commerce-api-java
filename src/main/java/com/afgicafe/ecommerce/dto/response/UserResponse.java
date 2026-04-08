package com.afgicafe.ecommerce.dto.response;

import com.afgicafe.ecommerce.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        UUID id,

        String name,

        String email,

        Gender gender,

        LocalDate dob,

        RoleSimpleResponse role,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("created_by")
        UUID createdBy,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt,

        @JsonProperty("updated_by")
        UUID updatedBy
) {}
