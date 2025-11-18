package com.afgicafe.ecommerce.controller;

import com.afgicafe.ecommerce.common.response.ApiResponse;
import com.afgicafe.ecommerce.dto.role.AssignPermissionsRequest;
import com.afgicafe.ecommerce.dto.role.RoleRequest;
import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.service.RoleService;
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
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService service;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Set<Role>>> index(){
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Roles retrieved successfully",
                "roles",
                service.getRoles()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Role>> store(@Valid @RequestBody RoleRequest request){
        Role role = service.createRole(request);

        var response = ApiResponse.success(
                HttpStatus.CREATED,
                "Roles created successfully",
                "role",
                role
        );

        return ResponseEntity
                .created(URI.create(String.format("/api/vi/roles/%s", role.getId())))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> show(@PathVariable UUID id){
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Role retrieved successfully",
                "role",
                service.getRole(id)
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Role>> update(@Valid @RequestBody RoleRequest request, @PathVariable UUID id){
        Role role = service.updateRole(request, id);

        var response = ApiResponse.success(
                HttpStatus.OK,
                "Roles updated successfully",
                "role",
                role
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable UUID id){
        service.deleteRole(id);
        var response = ApiResponse.success("Roles deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/assign-permission")
    public ResponseEntity<ApiResponse<Role>> assignPermissions(@RequestBody AssignPermissionsRequest request, @PathVariable UUID id){
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Roles updated successfully",
                "role",
                service.assignPermissionsToRole(request, id)
        );

        return ResponseEntity.ok(response);
    }
}
