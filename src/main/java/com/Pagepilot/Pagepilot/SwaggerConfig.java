package com.Pagepilot.Pagepilot;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PagePilot Library Management API")
                        .version("1.0")
                        .description("REST API for managing books, users, loans, and favorites in the PagePilot library system"));
    }
}