package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.entity.Permission;
import com.afgicafe.ecommerce.entity.Role;
import com.afgicafe.ecommerce.enums.PermissionEnum;
import com.afgicafe.ecommerce.enums.RoleEnum;
import com.afgicafe.ecommerce.repository.PermissionRepository;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class RoleTests extends AuthenticatedIntegrationTest {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PermissionRepository permissionRepository;

    private Role admin;
    private Role sale;
    private Role customer;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.flush();
        roleRepository.flush();

        admin = seedRole(RoleEnum.ADMIN);
        sale = seedRole(RoleEnum.SALE);
        customer = seedRole(RoleEnum.CUSTOMER);
    }

    private Role seedRole(RoleEnum e) {
        Role r = new Role();
        r.setName(e.name());
        return roleRepository.save(r);
    }

    private Permission seedPermission(PermissionEnum e) {
        Permission p = new Permission();
        p.setName(e.getValue());
        return permissionRepository.save(p);
    }

    @Test
    void shouldReturnPaginatedRoles() throws Exception {

        mockMvc.perform(get("/api/v1/roles")
                        .header("Authorization", bearerToken)
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles.content.length()").value(2))
                .andExpect(jsonPath("$.roles.total_elements").value(3));
    }

    @Test
    void shouldReturnRoleWithPermissions() throws Exception {

        Permission create = seedPermission(PermissionEnum.USER_CREATE);
        Permission delete = seedPermission(PermissionEnum.USER_DELETE);

        admin.setPermissions(List.of(create, delete));
        roleRepository.save(admin);

        mockMvc.perform(get("/api/v1/roles")
                        .header("Authorization", bearerToken)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles.content[?(@.name=='ADMIN')]").exists())
                .andExpect(jsonPath("$.roles.content[?(@.name=='ADMIN')].permissions.length()").value(2));
    }

    @Test
    void shouldReturnEmptyWhenNoRolesExist() throws Exception {

        roleRepository.deleteAllInBatch();

        mockMvc.perform(get("/api/v1/roles")
                        .header("Authorization", bearerToken)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles.content.length()").value(0))
                .andExpect(jsonPath("$.roles.total_elements").value(0));
    }

    @Test
    void shouldReturnRoleById() throws Exception {

        mockMvc.perform(get("/api/v1/roles/{id}", admin.getId())
                        .header("Authorization", bearerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role.id").value(admin.getId().toString()))
                .andExpect(jsonPath("$.role.name").value(RoleEnum.ADMIN.name()));
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {

        mockMvc.perform(get("/api/v1/roles/{id}", UUID.randomUUID())
                        .header("Authorization", bearerToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not Found"));
    }
}