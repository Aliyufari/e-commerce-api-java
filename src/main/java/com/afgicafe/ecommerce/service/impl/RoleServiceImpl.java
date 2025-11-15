package com.afgicafe.ecommerce.service.impl;

import com.afgicafe.ecommerce.common.exception.ResourceNotFoundException;
import com.afgicafe.ecommerce.dto.role.AssignPermissionsRequest;
import com.afgicafe.ecommerce.dto.role.RoleRequest;
import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.repository.PermissionRepository;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;

    @Override
    public Set<Role> getRoles() {
        return Set.copyOf(repository.findAll());
    }

    @Override
    public Role createRole(RoleRequest request) {
        Role role = new Role();
        role.setName(request.name());

        var permissions = permissionRepository.findAllById(request.permissionIds());

        if (permissions.size() != request.permissionIds().size()){
            throw new ResourceNotFoundException("One or more permissions not found");
        }

        role.setPermissions(Set.copyOf(permissions));

        return repository.save(role);
    }

    @Override
    public Role getRole(UUID id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format(
                                "Role not found with id '%s'", id
                        ))
                );
    }

    @Override
    public Role updateRole(RoleRequest request, UUID id) {
        Role role = getRole(id);
        role.setName(request.name());

        var permissions = permissionRepository.findAllById(request.permissionIds());

        if (permissions.size() != request.permissionIds().size()){
            throw new ResourceNotFoundException("One or more permissions not found");
        }

        role.setPermissions(Set.copyOf(permissions));

        return repository.save(role);
    }

    @Override
    public void deleteRole(UUID id) {
        Role role = getRole(id);
        repository.deleteById(id);
    }

    @Override
    public Role assignPermissionsToRole(AssignPermissionsRequest request, UUID id) {
        Role role = getRole(id);

        var permissions = permissionRepository.findAllById(request.permissionIds());
        if (permissions.size() != request.permissionIds().size()){
            throw new ResourceNotFoundException("One or more permissions not found");
        }

        role.getPermissions().addAll(permissions);
        return repository.save(role);
    }
}
