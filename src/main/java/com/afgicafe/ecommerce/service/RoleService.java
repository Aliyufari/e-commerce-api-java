package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RoleService {
    Page<RoleResponse> getRoles(Pageable pageable);

    RoleResponse getRole(UUID id);
}
