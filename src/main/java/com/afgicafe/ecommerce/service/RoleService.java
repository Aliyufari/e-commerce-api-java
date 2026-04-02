package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<RoleResponse> getRoles(Pageable pageable);
}
