package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.dto.request.LoginRequest;
import com.afgicafe.ecommerce.entity.User;
import com.afgicafe.ecommerce.enums.RoleEnum;
import com.afgicafe.ecommerce.repository.RoleRepository;
import com.afgicafe.ecommerce.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AuthenticatedIntegrationTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected RoleRepository roleRepository;
    @Autowired protected UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    protected String bearerToken;
    protected com.afgicafe.ecommerce.entity.Role adminRole;

    @BeforeEach
    void setUpBase() throws Exception {

        userRepository.deleteAll();
        roleRepository.deleteAll();

        adminRole = new com.afgicafe.ecommerce.entity.Role();
        adminRole.setName(RoleEnum.ADMIN.name());
        adminRole = roleRepository.save(adminRole);

        User admin = new User();
        admin.setName("Admin User");
        admin.setEmail("admin@email.com");
        admin.setPassword(passwordEncoder.encode("password123"));
        admin.setRole(adminRole);
        userRepository.save(admin);

        LoginRequest login = LoginRequest.builder()
                .email("admin@email.com")
                .password("password123")
                .build();

        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        bearerToken = "Bearer " + objectMapper.readTree(response)
                .at("/user/access_token")
                .asText();
    }
}