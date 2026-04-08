package com.afgicafe.ecommerce.mapper;

import com.afgicafe.ecommerce.dto.request.StoreUserRequest;
import com.afgicafe.ecommerce.dto.request.UpdateUserRequest;
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

    @Mapping(target = "password", ignore = true)
    void updateUser(UpdateUserRequest request, @MappingTarget User user);

    UserResponse toResponse(User user);
}
