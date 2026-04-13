package com.afgicafe.ecommerce.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        UserResponse user,
        String accessToken
) {}
