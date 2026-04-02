package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.response.PermissionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermissionService {
    Page<PermissionResponse> getPermissions(Pageable pageable);
}
