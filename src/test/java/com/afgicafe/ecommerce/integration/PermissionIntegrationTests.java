package com.afgicafe.ecommerce.integration;

import com.afgicafe.ecommerce.controller.PermissionController;
import com.afgicafe.ecommerce.repository.PermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@WebMvcTest(PermissionController.class)
//@AutoConfigureMockMvc
class PermissionIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PermissionRepository repository;

    @BeforeEach
    void setUp(){
//        Permission permission1 = Permission.builder()
//                .id(null)
//                .name(com.afgicafe.ecommerce.enums.Permission.USER_VIEW.getValue())
//                .build();
//        Permission permission2 = Permission.builder()
//                .id(null)
//                .name(com.afgicafe.ecommerce.enums.Permission.USER_CREATE.getValue())
//                .build();
//        Permission permission3 = Permission.builder()
//                .id(null)
//                .name(com.afgicafe.ecommerce.enums.Permission.USER_UPDATE.getValue())
//                .build();
//        Permission permission4 = Permission.builder()
//                .id(null)
//                .name(com.afgicafe.ecommerce.enums.Permission.USER_DELETE.getValue())
//                .build();
//
//        repository.saveAll(List.of(permission1, permission2, permission3, permission4));
    }

    @Test
    void shouldReturnPaginatedPermissionsFromDb() throws Exception {
//        var result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/permissions")
//                .param("page", "0")
//                .param("size", "2")
//                .param("sort", "name,asc")
//                .accept(MediaType.APPLICATION_JSON));
//
//        result.andExpect(MockMvcResultMatchers.status().isOk());

//                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElement").value(4))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0]")
//                        .value(com.afgicafe.ecommerce.enums.Permission.USER_VIEW.getValue()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[3]")
//                        .value(com.afgicafe.ecommerce.enums.Permission.USER_DELETE.getValue()));
    }
}
