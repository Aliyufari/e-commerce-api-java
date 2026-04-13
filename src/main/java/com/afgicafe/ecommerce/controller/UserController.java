package com.afgicafe.ecommerce.controller;

import com.afgicafe.ecommerce.dto.request.StoreUserRequest;
import com.afgicafe.ecommerce.dto.request.UpdateUserRequest;
import com.afgicafe.ecommerce.dto.response.UserResponse;
import com.afgicafe.ecommerce.helper.ApiResponse;
import com.afgicafe.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> index(Pageable pageable){
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Users retrieved successfully",
                "users",
                service.getUsers(pageable)
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> store(
            @Valid @RequestBody StoreUserRequest request,
            UriComponentsBuilder ucb
    ) {
        var user = service.createUser(request);
        var response = ApiResponse.success(
                HttpStatus.CREATED,
                "User created successfully",
                "user",
                user
        );

        URI path = ucb.path("/api/v1/users/{id}")
                .buildAndExpand(user.id())
                .toUri();

        return ResponseEntity.created(path).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> show(@PathVariable UUID id) {
        var response = ApiResponse.success(
                HttpStatus.OK,
                "User retrieved successfully",
                "user",
                service.getUser(id)
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update (
            @Valid @RequestBody UpdateUserRequest request,
            @PathVariable UUID id
    ) {
        var response = ApiResponse.success(
                HttpStatus.OK,
                "User updated successfully",
                "user",
                service.updateUser(request, id)
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> destroy (@PathVariable UUID id) {
        service.deleteUser(id);

        return ResponseEntity.ok(
                ApiResponse.success("User Deleted successfully")
        );
    }
}
