package com.example.job_application_eval.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CodePioneers – Job Application Evaluation API")
                        .description("CodePioneers' Career Portal is a demo recruitment platform. Applicants submit their CVs, and recruiters compare candidates side by side, helped by an AI-powered FastAPI micro-service that scores every applicant in real time.\n")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Rigel Hadushi")
                                .url("https://github.com/rigelHadushiDev")
                                .email("rigelhadushi4@gmail.com"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Source code & issues")
                        .url("https://github.com/rigelHadushiDev/job-eval-backend-spring"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
