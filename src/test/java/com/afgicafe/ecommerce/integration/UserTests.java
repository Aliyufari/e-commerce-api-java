package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.controller.UserController;
import com.afgicafe.ecommerce.dto.request.StoreUserRequest;
import com.afgicafe.ecommerce.dto.request.UpdateUserRequest;
import com.afgicafe.ecommerce.dto.response.RoleResponse;
import com.afgicafe.ecommerce.dto.response.UserResponse;
import com.afgicafe.ecommerce.enums.Gender;
import com.afgicafe.ecommerce.enums.Role;
import com.afgicafe.ecommerce.exception.ResourceNotFoundException;
import com.afgicafe.ecommerce.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService service;

    @Test
    void shouldReturnPageableUserWithTheirRoles() throws Exception {
        List<UserResponse> users = List.of(
                UserResponse.builder()
                        .id(UUID.randomUUID())
                        .name("John Doe")
                        .email("jd@email.com")
                        .dob(LocalDate.now())
                        .role(
                                RoleResponse.builder()
                                        .id(UUID.randomUUID())
                                        .name(String.valueOf(Role.ADMIN))
                                        .build()
                        )
                        .build(),

                UserResponse.builder()
                        .id(UUID.randomUUID())
                        .name("John Smith")
                        .email("js@email.com")
                        .dob(LocalDate.now())
                        .role(
                                RoleResponse.builder()
                                        .id(UUID.randomUUID())
                                        .name(String.valueOf(Role.SALE))
                                        .build()
                        )
                        .build(),

                UserResponse.builder()
                        .id(UUID.randomUUID())
                        .name("John Smith")
                        .email("js@email.com")
                        .dob(LocalDate.now())
                        .role(
                                RoleResponse.builder()
                                        .id(UUID.randomUUID())
                                        .name(String.valueOf(Role.CUSTOMER))
                                        .build()
                        )
                        .build()
        );

        var pageContent = users.subList(0, 2);

        var page = new PageImpl<>(pageContent, PageRequest.of(0, 2), 3);

        when(service.getUsers(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users.content.length()").value(2))
                .andExpect(jsonPath("$.users.total_elements").value(3))
                .andExpect(jsonPath("$.users.content[0].name").value("John Doe"))
                .andExpect(jsonPath("$.users.content[1].email").value(users.get(1).email()))
                .andExpect(jsonPath("$.users.content[0].role.name").value(Role.ADMIN.name()));
    }

    @Test
    void shouldCreateUserWhenProvideAValidJsonAndReturnCreatedUserJson() throws Exception {
        RoleResponse role =RoleResponse.builder()
                .id(UUID.randomUUID())
                .name(String.valueOf(Role.ADMIN))
                .build();

        StoreUserRequest request = StoreUserRequest.builder()
                .name("John Doe")
                .email("john@email.com")
                .gender(Gender.MALE)
                .roleId(role.id())
                .dob(LocalDate.parse("2000-01-01"))
                .build();

        String json = objectMapper.writeValueAsString(request);

        UserResponse response = UserResponse.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john@email.com")
                .role(role)
                .dob(LocalDate.parse("2000-01-01"))
                .build();

        when(service.createUser(any(StoreUserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.user.name").value("John Doe"))
                .andExpect(jsonPath("$.user.email").value("john@email.com"))
                .andExpect(jsonPath("$.user.role.name").value(Role.ADMIN.name()));

        verify(service, times(1))
                .createUser(any(StoreUserRequest.class));
    }

    @Test
    void shouldReturnUserWhenProvideValidId() throws Exception {
        UserResponse user = UserResponse.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("jd@email.com")
                .dob(LocalDate.now())
                .role(
                        RoleResponse.builder()
                                .id(UUID.randomUUID())
                                .name(String.valueOf(Role.ADMIN))
                                .build()
                )
                .build();

        when(service.getUser(any(UUID.class))).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", user.id())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value(user.email()));
    }

    @Test
    void shouldReturnNotFoundWhenProvideInvalidId() throws Exception {
        UUID id = UUID.randomUUID();

        when(service.getUser(id)).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/api/v1/users/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateUserWhenProvideAValidJsonAndReturnCreatedUserJson() throws Exception {
        RoleResponse role = RoleResponse.builder()
                .id(UUID.randomUUID())
                .name(String.valueOf(Role.ADMIN))
                .build();

        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("John Doe")
                .email("john@email.com")
                .gender(Gender.MALE)
                .roleId(role.id())
                .dob(LocalDate.parse("2000-01-01"))
                .build();

        String json = objectMapper.writeValueAsString(request);

        UserResponse response = UserResponse.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john@email.com")
                .role(role)
                .dob(LocalDate.parse("2000-01-01"))
                .build();

        when(service.createUser(any(StoreUserRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.user.name").value("John Doe"))
                .andExpect(jsonPath("$.user.email").value("john@email.com"))
                .andExpect(jsonPath("$.user.role.name").value(Role.ADMIN.name()));

        verify(service, times(1))
                .createUser(any(StoreUserRequest.class));
    }
}
