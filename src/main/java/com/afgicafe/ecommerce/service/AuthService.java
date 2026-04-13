package com.afgicafe.ecommerce.service;

import com.afgicafe.ecommerce.dto.request.LoginRequest;
import com.afgicafe.ecommerce.dto.request.RegisterRequest;
import com.afgicafe.ecommerce.dto.response.LoginResponse;
import com.afgicafe.ecommerce.dto.response.UserResponse;

public interface AuthService {
    UserResponse register (RegisterRequest request);

    LoginResponse verify (LoginRequest request);
}
