package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.user.UserRequest;
import com.afgicafe.ecommerce.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getUsers();

    User createUser(UserRequest request);

    User getUser(UUID id);

    User updateUser(UUID id, UserRequest request);

    void deleteUser(UUID id);
}
