package com.afgicafe.ecommerce.service.impl;

import com.afgicafe.ecommerce.dto.request.LoginRequest;
import com.afgicafe.ecommerce.dto.request.RegisterRequest;
import com.afgicafe.ecommerce.dto.response.LoginResponse;
import com.afgicafe.ecommerce.dto.response.UserResponse;
import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.entity.User;
import com.afgicafe.ecommerce.enums.RoleEnum;
import com.afgicafe.ecommerce.exception.BadRequestException;
import com.afgicafe.ecommerce.mapper.UserMapper;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.repository.UserRepository;
import com.afgicafe.ecommerce.security.JwtAuthenticationService;
import com.afgicafe.ecommerce.security.UserPrincipal;
import com.afgicafe.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final UserMapper mapper;
    private final JwtAuthenticationService jwtAuthService;


    @Override
    public UserResponse register(RegisterRequest request) {
        User user = mapper.toEntity(request);
        Role role = roleRepository.findByName(RoleEnum.CUSTOMER.name())
                .orElseThrow(() -> new BadRequestException("Role not found"));

        user.setRole(role);
        user.setPassword(encoder.encode(request.getPassword()));

        User savedUser = repository.save(user);

        return mapper.toResponse(savedUser);
    }

    @Override
    public LoginResponse verify(LoginRequest request) {
        Authentication authenticate = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserPrincipal principal = (UserPrincipal) authenticate.getPrincipal();

        final String token = jwtAuthService.generateAccessToken(principal);

        return LoginResponse.builder()
                .user(mapper.toResponse(principal.getUser()))
                .accessToken(token)
                .build();
    }
}
