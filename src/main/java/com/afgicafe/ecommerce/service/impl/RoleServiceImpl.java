package com.afgicafe.ecommerce.service.impl;

import com.afgicafe.ecommerce.dto.response.RoleResponse;
import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.exception.ResourceNotFoundException;
import com.afgicafe.ecommerce.mapper.RoleMapper;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    @Override
    public Page<RoleResponse> getRoles(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public RoleResponse getRole(UUID id) {
        Role role = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(
                        String.format("No role found with id %s", id)
                )
        );

        return mapper.toResponse(role);
    }
}
