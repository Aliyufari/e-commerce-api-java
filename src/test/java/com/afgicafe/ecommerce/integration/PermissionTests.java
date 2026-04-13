package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.entity.Permission;
import com.afgicafe.ecommerce.enums.PermissionEnum;
import com.afgicafe.ecommerce.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PermissionTests extends AuthenticatedIntegrationTest {

    @Autowired
    PermissionRepository permissionRepository;

    @BeforeEach
    void setUp() {
        permissionRepository.deleteAll();
        permissionRepository.flush();
    }

    private void seed(PermissionEnum e) {
        Permission p = new Permission();
        p.setName(e.getValue());
        permissionRepository.save(p);
    }

    @Test
    void shouldReturnPaginatedPermissions() throws Exception {

        seed(PermissionEnum.USER_VIEW);
        seed(PermissionEnum.USER_CREATE);
        seed(PermissionEnum.USER_UPDATE);
        seed(PermissionEnum.USER_DELETE);

        mockMvc.perform(get("/api/v1/permissions")
                        .header("Authorization", bearerToken)
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissions.content.length()").value(2))
                .andExpect(jsonPath("$.permissions.total_elements").value(4));
    }

    @Test
    void shouldReturnAllWhenPageSizeIsLarge() throws Exception {

        seed(PermissionEnum.USER_VIEW);
        seed(PermissionEnum.USER_CREATE);

        mockMvc.perform(get("/api/v1/permissions")
                        .header("Authorization", bearerToken)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissions.content.length()").value(2))
                .andExpect(jsonPath("$.permissions.total_elements").value(2));
    }

    @Test
    void shouldReturnEmptyPageWhenNoData() throws Exception {

        mockMvc.perform(get("/api/v1/permissions")
                        .header("Authorization", bearerToken)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissions.content.length()").value(0))
                .andExpect(jsonPath("$.permissions.total_elements").value(0));
    }
}