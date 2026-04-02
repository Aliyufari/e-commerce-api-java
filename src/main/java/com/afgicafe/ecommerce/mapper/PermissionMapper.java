package com.afgicafe.ecommerce.mapper;

import com.afgicafe.ecommerce.dto.response.PermissionResponse;
import com.afgicafe.ecommerce.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR
)
public interface PermissionMapper {
    PermissionResponse toResponse (Permission permission);
}
