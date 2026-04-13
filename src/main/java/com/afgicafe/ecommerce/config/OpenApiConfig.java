package com.afgicafe.ecommerce.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi () {
        SecurityRequirement requirement = new SecurityRequirement()
                .addList("bearerAuth");

        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("LOCAL ENVIRONMENT");

        return new OpenAPI()
                .info(new Info()
                        .title("Ecommerce App API")
                        .version("1.0")
                        .description("An ecommerce application API")
                )
                .tags(
                        List.of(
                            new Tag().name("Auth"),
                            new Tag().name("Permissions"),
                            new Tag().name("Roles"),
                            new Tag().name("Users")
                        )
                )
                .servers(List.of(devServer))
                .addSecurityItem(requirement);
    }
}
