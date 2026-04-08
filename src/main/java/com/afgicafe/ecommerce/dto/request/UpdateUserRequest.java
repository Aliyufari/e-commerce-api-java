package com.afgicafe.ecommerce.dto.request;

import com.afgicafe.ecommerce.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class UpdateUserRequest {
    private String name;

    @Email(message = "Email must be valid")
    private String email;

    private Gender gender;

    private LocalDate dob;

    @JsonProperty("role_id")
    private UUID roleId;

    public boolean isEmpty() {
        return name == null &&
                email == null &&
                gender == null &&
                dob == null &&
                roleId == null;
    }
}
