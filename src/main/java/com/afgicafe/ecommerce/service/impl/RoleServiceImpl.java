package com.afgicafe.ecommerce.service.impl;

import com.afgicafe.ecommerce.dto.response.RoleResponse;
import com.afgicafe.ecommerce.mapper.RoleMapper;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    @Override
    public Page<RoleResponse> getRoles(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }
}
