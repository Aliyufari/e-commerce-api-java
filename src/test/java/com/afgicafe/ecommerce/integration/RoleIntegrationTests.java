package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.controller.RoleController;
import com.afgicafe.ecommerce.dto.response.PermissionResponse;
import com.afgicafe.ecommerce.dto.response.RoleResponse;
import com.afgicafe.ecommerce.enums.Permission;
import com.afgicafe.ecommerce.enums.Role;
import com.afgicafe.ecommerce.exception.ResourceNotFoundException;
import com.afgicafe.ecommerce.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc
public class RoleIntegrationTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoleService service;

    @Test
    void shouldReturnPaginatedRoles() throws Exception {
        List<RoleResponse> roles = List.of(
                RoleResponse.builder()
                        .id(UUID.randomUUID())
                        .name(String.valueOf(Role.ADMIN))
                        .build(),
                RoleResponse.builder()
                        .id(UUID.randomUUID())
                        .name(String.valueOf(Role.SALE))
                        .build(),
                RoleResponse.builder()
                        .id(UUID.randomUUID())
                        .name(String.valueOf(Role.CUSTOMER))
                        .build()
        );

        var pageContent = roles.subList(0, 2);

        Page<RoleResponse> page = new PageImpl(pageContent, PageRequest.of(0, 2), 3);

        when(service.getRoles(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles")
                .param("page", "0")
                .param("size", "3")
                .param("sort", "name,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles.content.length()").value(2))
                .andExpect(jsonPath("$.roles.total_elements").value(3))
                .andExpect(jsonPath("$.roles.content[0].name").value(String.valueOf(Role.ADMIN)))
                .andExpect(jsonPath("$.roles.content[1].name").value(String.valueOf(Role.SALE)));
    }

    @Test
    void shouldReturnPaginatedRolesWithTheirPermissions() throws Exception {
        List<PermissionResponse> permissionResponses = List.of(
                PermissionResponse.builder()
                        .id(UUID.randomUUID())
                        .name(Permission.USER_CREATE.getValue())
                        .build(),
                PermissionResponse.builder()
                        .id(UUID.randomUUID())
                        .name(Permission.USER_DELETE.getValue())
                        .build()
        );

        List<RoleResponse> roles = List.of(
                RoleResponse.builder()
                        .id(UUID.randomUUID())
                        .name(String.valueOf(Role.ADMIN))
                        .permissions(permissionResponses)
                        .build(),
                RoleResponse.builder()
                        .id(UUID.randomUUID())
                        .name(String.valueOf(Role.SALE))
                        .permissions(List.of())
                        .build(),
                RoleResponse.builder()
                        .id(UUID.randomUUID())
                        .name(String.valueOf(Role.CUSTOMER))
                        .permissions(List.of())
                        .build()
        );

        var pageContent = roles.subList(0, 2);

        Page<RoleResponse> page = new PageImpl(pageContent, PageRequest.of(0, 2), 3);

        when(service.getRoles(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles")
                        .param("page", "0")
                        .param("size", "3")
                        .param("sort", "name,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles.content[0].permissions.length()").value(2))
                .andExpect(jsonPath("$.roles.content[0].permissions[0].name")
                        .value(Permission.USER_CREATE.getValue()))
                .andExpect(jsonPath("$.roles.content[1].permissions").isEmpty());
    }

    @Test
    void shouldReturnRoleWhenParseValidId() throws Exception {
        RoleResponse role = RoleResponse.builder()
                .id(UUID.randomUUID())
                .name(String.valueOf(Role.ADMIN))
                .build();

        when(service.getRole(any(UUID.class))).thenReturn(role);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/{id}", role.id())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role.id").value(role.id().toString()))
                .andExpect(jsonPath("$.role.name").value(String.valueOf(Role.ADMIN)));
    }

    @Test
    void shouldReturnNotFoundWhenParseInvalidId() throws Exception {
        UUID id = UUID.randomUUID();

        when(service.getRole(id)).thenThrow(new ResourceNotFoundException("Not Found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
