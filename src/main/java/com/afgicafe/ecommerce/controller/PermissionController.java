package com.afgicafe.ecommerce.controller;

import com.afgicafe.ecommerce.common.response.ApiResponse;
import com.afgicafe.ecommerce.dto.permission.PermissionRequest;
import com.afgicafe.ecommerce.entity.Permission;
import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    private final PermissionService service;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Set<Permission>>> index(){
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Permissions retrieved successfully",
                "permissions",
                service.getPermissions()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Permission>> store(@Valid @RequestBody PermissionRequest request) {
        Permission permission = service.createPermission(request);
        var response = ApiResponse.success(
                HttpStatus.CREATED,
                "Permissions created successfully",
                "permission",
                permission
        );

        return ResponseEntity
                .created(URI.create(String.format("/api/vi/permissions/%s", permission.getId())))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Permission>> show(@PathVariable UUID id){
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Permission retrieved successfully",
                "permission",
                service.getPermission(id)
        );

        return ResponseEntity.ok(response);
    }
}
