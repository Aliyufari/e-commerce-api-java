package com.afgicafe.ecommerce.controller;

import com.afgicafe.ecommerce.dto.request.LoginRequest;
import com.afgicafe.ecommerce.dto.request.RegisterRequest;
import com.afgicafe.ecommerce.dto.response.LoginResponse;
import com.afgicafe.ecommerce.dto.response.UserResponse;
import com.afgicafe.ecommerce.helper.ApiResponse;
import com.afgicafe.ecommerce.repository.UserRepository;
import com.afgicafe.ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
public class AuthController {

    private final AuthService service;

    @Operation(
            description = "User Registration Route",
            summary = "User Registration",
            security = @SecurityRequirement(name = "")
    )
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register (@Valid @RequestBody RegisterRequest request) {
        var response = ApiResponse.success(
                HttpStatus.OK,
                "User registered successfully",
                "user",
                service.register(request)
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "User Login Route",
            summary = "User Login",
            security = @SecurityRequirement(name = "")
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login (@Valid @RequestBody LoginRequest request) {
        var response = ApiResponse.success(
                HttpStatus.OK,
                "User logged in successfully",
                "user",
                service.verify(request)
        );

        return ResponseEntity.ok(response);
    }
}
