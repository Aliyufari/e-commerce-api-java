package com.afgicafe.ecommerce.mapper;

import com.afgicafe.ecommerce.dto.response.RoleResponse;
import com.afgicafe.ecommerce.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface RoleMapper {
    RoleResponse toResponse(Role role);
}
