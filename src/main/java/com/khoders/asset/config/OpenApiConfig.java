package com.khoders.asset.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().components(new Components()).info(new Info()
                .title("EAM Software - Khoders Technologies")
                .description("Swagger Configuration for Application")
                .version("v1.0.0")
        );
    }
}
