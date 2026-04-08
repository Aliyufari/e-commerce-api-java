package com.afgicafe.ecommerce.service.impl;

import com.afgicafe.ecommerce.dto.request.StoreUserRequest;
import com.afgicafe.ecommerce.dto.request.UpdateUserRequest;
import com.afgicafe.ecommerce.dto.response.UserResponse;
import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.entity.User;
import com.afgicafe.ecommerce.exception.BadRequestException;
import com.afgicafe.ecommerce.exception.ResourceNotFoundException;
import com.afgicafe.ecommerce.mapper.UserMapper;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.repository.UserRepository;
import com.afgicafe.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;

    @Override
    public Page<UserResponse> getUsers(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public UserResponse getUser(UUID id) {
        User user = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User not found with id %s ", id))
        );

        return mapper.toResponse(user);
    }

    @Override
    public UserResponse createUser(StoreUserRequest request) {
        User newUser = mapper.toEntity(request);

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        newUser.setRole(role);

        var user = repository.save(newUser);

        return mapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request, UUID id) {

        if (request.isEmpty()) {
            throw new BadRequestException("At least one field must be provided");
        }

        User user = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        mapper.updateUser(request, user);

        if (request.getRoleId() != null){
            Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() ->
                    new ResourceNotFoundException("Role not found"));

            user.setRole(role);
        }

        User updatedUser = repository.save(user);

        return mapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = repository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        repository.delete(user);
    }

}
