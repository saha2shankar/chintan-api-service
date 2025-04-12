package com.chintan.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("Chintan API")
                .description("CHINTAN API")
                .version("1.0.0")
                .termsOfService("http://harishankar.info.np")
                .contact(new Contact()
                        .email("sahharishankar11@gmail.com")
                        .name("Harishankar Sah")
                        .url("http://harishankar.info.np"));

        List<Server> serverList = List.of(
                new Server().description("Dev").url("http://localhost:8080"),
                new Server().description("Test").url("http://localhost:8081"),
                new Server().description("Prod").url("http://localhost:8082")
        );

        io.swagger.v3.oas.models.security.SecurityScheme securityScheme = 
                new io.swagger.v3.oas.models.security.SecurityScheme()
                        .name("Authorization")
                        .scheme("bearer")
                        .type(Type.HTTP)
                        .bearerFormat("JWT")
                        .in(In.HEADER);

        Components component = new Components().addSecuritySchemes("Token", securityScheme);

        return new OpenAPI()
                .servers(serverList)
                .info(info)
                .components(component)
                .addSecurityItem(new SecurityRequirement().addList("Token"));
    }
}
