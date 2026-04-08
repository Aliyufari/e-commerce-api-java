package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.request.StoreUserRequest;
import com.afgicafe.ecommerce.dto.request.UpdateUserRequest;
import com.afgicafe.ecommerce.dto.response.UserResponse;
import com.afgicafe.ecommerce.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Page<UserResponse> getUsers(Pageable pageable);

    UserResponse getUser(UUID id);

    UserResponse createUser(StoreUserRequest request);

    UserResponse updateUser(UpdateUserRequest request, UUID id);

    void deleteUser(UUID id);
}
