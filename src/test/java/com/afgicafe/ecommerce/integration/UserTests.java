package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.dto.request.StoreUserRequest;
import com.afgicafe.ecommerce.dto.request.UpdateUserRequest;
import com.afgicafe.ecommerce.enums.Gender;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
public class UserTests extends AuthenticatedIntegrationTest {

    private String toJson(String name, String email, UUID roleId) throws Exception {
        StoreUserRequest req = StoreUserRequest.builder()
                .name(name)
                .email(email)
                .password("password123")
                .gender(Gender.MALE)
                .roleId(roleId)
                .dob(LocalDate.parse("2000-01-01"))
                .build();

        return objectMapper.writeValueAsString(req);
    }

    private String createUser(String name, String email) throws Exception {
        String res = mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(name, email, adminRole.getId())))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(res).path("user").path("id").asText();
    }

    @Test
    void shouldReturnUnauthorizedWhenNoTokenProvided() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreateUserSuccessfully() throws Exception {

        mockMvc.perform(post("/api/v1/users")
                        .header("Authorization", bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson("John Doe", "john@email.com", adminRole.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.name").value("John Doe"));

        assertThat(userRepository.count()).isEqualTo(2);
    }

    @Test
    void shouldReturnPageableUsersWithTheirRoles() throws Exception {

        createUser("John Doe", "jd@email.com");
        createUser("Jane Doe", "jane@email.com");

        mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", bearerToken)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users.content.length()").value(3))
                .andExpect(jsonPath("$.users.total_elements").value(3))
                .andExpect(jsonPath("$.users.pageable.page_number").value(0))
                .andExpect(jsonPath("$.users.pageable.page_size").value(10));
    }

    @Test
    void shouldReturnUsersSortedByName() throws Exception {

        createUser("Zack", "z@email.com");
        createUser("Adam", "a@email.com");

        mockMvc.perform(get("/api/v1/users")
                        .header("Authorization", bearerToken)
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users.content[0].name").value("Adam"));
    }

    @Test
    void shouldUpdateUserSuccessfully() throws Exception {

        String id = createUser("John Doe", "john@email.com");

        UpdateUserRequest req = UpdateUserRequest.builder()
                .name("Updated Name")
                .email("updated@email.com")
                .gender(Gender.MALE)
                .roleId(adminRole.getId())
                .dob(LocalDate.parse("2000-01-01"))
                .build();

        mockMvc.perform(put("/api/v1/users/{id}", id)
                        .header("Authorization", bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        assertThat(userRepository.findById(UUID.fromString(id)))
                .get()
                .extracting("name")
                .isEqualTo("Updated Name");
    }

    @Test
    void shouldDeleteUserSuccessfully() throws Exception {

        String id = createUser("John Doe", "john@email.com");

        mockMvc.perform(delete("/api/v1/users/{id}", id)
                        .header("Authorization", bearerToken))
                .andExpect(status().isOk());

        assertThat(userRepository.existsById(UUID.fromString(id))).isFalse();
    }
}