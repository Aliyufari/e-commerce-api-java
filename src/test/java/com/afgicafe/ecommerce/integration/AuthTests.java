package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.dto.request.LoginRequest;
import com.afgicafe.ecommerce.entity.User;
import com.afgicafe.ecommerce.enums.RoleEnum;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private com.afgicafe.ecommerce.entity.Role customerRole;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        customerRole = new com.afgicafe.ecommerce.entity.Role();
        customerRole.setName(RoleEnum.CUSTOMER.name());
        roleRepository.save(customerRole);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        String json = """
                {
                    "name": "John Doe",
                    "email": "john@email.com",
                    "password": "password123",
                    "gender": "MALE",
                    "dob": "2000-01-01"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.user.email").value("john@email.com"));
    }

    @Test
    void shouldReturnBadRequestWhenRegisterEmailIsMissing() throws Exception {
        String json = """
                {
                    "name": "John Doe",
                    "password": "password123",
                    "gender": "MALE",
                    "dob": "2000-01-01"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenRegisterPasswordIsMissing() throws Exception {
        String json = """
                {
                    "name": "John Doe",
                    "email": "john@email.com",
                    "gender": "MALE",
                    "dob": "2000-01-01"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldLoginSuccessfullyAndReturnToken() throws Exception {
        // Seed a user directly with encoded password
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@email.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(customerRole);
        userRepository.save(user);

        LoginRequest login = LoginRequest.builder()
                .email("john@email.com")
                .password("password123")
                .build();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User logged in successfully"))
                .andExpect(jsonPath("$.user.access_token").isNotEmpty());
    }

    @Test
    void shouldReturnUnauthorizedWhenCredentialsAreWrong() throws Exception {
        LoginRequest login = LoginRequest.builder()
                .email("nonexistent@email.com")
                .password("wrongpassword")
                .build();

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnBadRequestWhenLoginEmailIsMissing() throws Exception {
        String json = """
                {
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenLoginPasswordIsMissing() throws Exception {
        String json = """
                {
                    "email": "john@email.com"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}