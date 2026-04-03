package com.afgicafe.ecommerce.controller;

import com.afgicafe.ecommerce.dto.response.RoleResponse;
import com.afgicafe.ecommerce.helper.ApiResponse;
import com.afgicafe.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService service;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<RoleResponse>>> index(Pageable pageable) {
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Roles retrieved successfully",
                "roles",
                service.getRoles(pageable)
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> show(@PathVariable(name = "id") UUID id){
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Role retrieved successfully",
                "role",
                service.getRole(id)
        );

        return ResponseEntity.ok(response);
    }
}
