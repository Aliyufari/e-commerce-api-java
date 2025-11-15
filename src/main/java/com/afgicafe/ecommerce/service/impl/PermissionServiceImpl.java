package com.afgicafe.ecommerce.service.impl;

import com.afgicafe.ecommerce.dto.permission.PermissionRequest;
import com.afgicafe.ecommerce.entity.Permission;
import com.afgicafe.ecommerce.repository.PermissionRepository;
import com.afgicafe.ecommerce.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository repository;

    @Override
    public Set<Permission> getPermissions() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public Permission createPermission(PermissionRequest request) {
        Permission permission = new Permission();
        permission.setName(request.name());
        return repository.save(permission);
    }
}
