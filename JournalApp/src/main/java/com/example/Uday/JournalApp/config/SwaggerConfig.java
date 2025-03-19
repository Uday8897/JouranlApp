package com.example.Uday.JournalApp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info().title("Journal App").description("By Uday Kumar"))
                .servers(Arrays.asList(new Server().url("http://localhost:8081").description("Local"),new Server().url("http:://localhost:8082").description("Production ")))
                .tags(Arrays.asList(new Tag().name("Public User Controller"),new Tag().name("User Controller"),new Tag().name("Journal Entry Controller"),new Tag().name("Admin Controller")));
    }
}
