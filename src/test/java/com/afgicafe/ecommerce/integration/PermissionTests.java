package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.controller.PermissionController;
import com.afgicafe.ecommerce.dto.response.PermissionResponse;
import com.afgicafe.ecommerce.enums.Permission;
import com.afgicafe.ecommerce.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PermissionController.class)
@AutoConfigureMockMvc
class PermissionTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PermissionService service;

    @Test
    void shouldReturnPaginatedPermissions() throws Exception {
        List<PermissionResponse> permissions = List.of(
                PermissionResponse.builder()
                        .name(Permission.USER_VIEW.getValue())
                        .build(),

                PermissionResponse.builder()
                        .name(Permission.USER_CREATE.getValue())
                        .build(),

                PermissionResponse.builder()
                        .name(Permission.USER_UPDATE.getValue())
                        .build(),

                PermissionResponse.builder()
                        .name(Permission.USER_DELETE.getValue())
                        .build()
        );

        var pageContent = permissions.subList(0, 2);

        var page = new PageImpl<>(pageContent, PageRequest.of(0, 2), 4);

        when(service.getPermissions(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permissions")
                        .param("page", "0")
                        .param("size", "2")
                        .param("sort", "name,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permissions.content.length()").value(2))
                .andExpect(jsonPath("$.permissions.total_elements").value(4))
                .andExpect(jsonPath("$.permissions.content[0].name").value(Permission.USER_VIEW.getValue()))
                .andExpect(jsonPath("$.permissions.content[1].name").value(Permission.USER_CREATE.getValue()));
    }
}
