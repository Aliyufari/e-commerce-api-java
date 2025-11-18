package com.afgicafe.ecommerce.dto.user;

import com.afgicafe.ecommerce.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record UserRequest(
        @NotBlank(message = "Name field is required")
        String name,

        @Email(message = "Invalid email address")
        @NotBlank(message = "Email field is required")
        String email,

        @NotNull(message = "Gender field is required")
        Gender gender,

        @NotNull(message = "Date of birth field is required")
        LocalDate dob,

        @NotBlank(message = "Password field is required")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password,

        @NotNull(message = "Role ID is required")
        UUID roleId
) {}
