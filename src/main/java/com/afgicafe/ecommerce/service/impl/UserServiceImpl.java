package com.afgicafe.ecommerce.service.impl;

import com.afgicafe.ecommerce.dto.user.UserRequest;
import com.afgicafe.ecommerce.entity.User;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.repository.UserRepository;
import com.afgicafe.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> getUsers() {
        return List.of();
    }

    @Override
    public User createUser(UserRequest request) {
        return null;
    }

    @Override
    public User getUser(UUID id) {
        return null;
    }

    @Override
    public User updateUser(UUID id, UserRequest request) {
        return null;
    }

    @Override
    public void deleteUser(UUID id) {

    }
}
