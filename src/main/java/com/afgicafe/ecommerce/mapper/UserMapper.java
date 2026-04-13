package com.afgicafe.ecommerce.mapper;

import com.afgicafe.ecommerce.dto.request.StoreUserRequest;
import com.afgicafe.ecommerce.dto.request.UpdateUserRequest;
import com.afgicafe.ecommerce.dto.request.RegisterRequest;
import com.afgicafe.ecommerce.dto.response.UserResponse;
import com.afgicafe.ecommerce.entity.User;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    User toEntity(StoreUserRequest request);

    User toEntity(RegisterRequest request);

    @Mapping(target = "password", ignore = true)
    void updateUser(UpdateUserRequest request, @MappingTarget User user);

    @Mapping(target = "role.permissions", ignore = true)
    UserResponse toResponse(User user);
}
