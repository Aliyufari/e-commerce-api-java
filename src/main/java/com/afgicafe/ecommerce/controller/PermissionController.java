package com.afgicafe.ecommerce.controller;

import com.afgicafe.ecommerce.dto.request.UpdatePermissionRequest;
import com.afgicafe.ecommerce.dto.response.PermissionResponse;
import com.afgicafe.ecommerce.helper.ApiResponse;
import com.afgicafe.ecommerce.dto.request.StorePermissionRequest;
import com.afgicafe.ecommerce.entity.Permission;
import com.afgicafe.ecommerce.mapper.PermissionMapper;
import com.afgicafe.ecommerce.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<PermissionResponse>>> index(Pageable pageable){
        var response = ApiResponse.success(
                HttpStatus.OK,
                "Permissions retrieved successfully",
                "permissions",
                service.getPermissions(pageable)
        );

        return ResponseEntity.ok(response);
    }
}
