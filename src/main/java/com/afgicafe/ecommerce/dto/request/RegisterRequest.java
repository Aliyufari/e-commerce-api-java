package com.afgicafe.ecommerce.dto.request;

import com.afgicafe.ecommerce.entity.User;
import com.afgicafe.ecommerce.enums.Gender;
import com.afgicafe.ecommerce.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
//    @Unique(entity = User.class, message = "Email already taken")
    private String email;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth required")
    private LocalDate dob;

    @NotBlank(message = "Password is required")
    @Size(
            min = 8,
            message = "Password minimum 8 characters"
    )
    private String password;
}
