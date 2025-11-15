package com.afgicafe.ecommerce.dto.user;

import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        Gender gender,
        LocalDate dob,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
