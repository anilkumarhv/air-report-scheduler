package com.anil.airreportscheduler.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI aviationOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Aviation Report Scheduler API")
                .description("API for retrieving and managing aviation weather reports (PIREP and METAR) from NOAA ADDS")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Anil Kumar")
                    .url("https://github.com/anilkumarhv/air-report-scheduler")))
            .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
            .schemaRequirement("basicAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic"));
    }
}
