package com.afgicafe.ecommerce.service.impl;

import com.afgicafe.ecommerce.dto.response.PermissionResponse;
import com.afgicafe.ecommerce.mapper.PermissionMapper;
import com.afgicafe.ecommerce.repository.PermissionRepository;
import com.afgicafe.ecommerce.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;
    private final PermissionMapper mapper;

    @Override
    public Page<PermissionResponse> getPermissions(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }
}
