package com.application.integration_task;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class IntegrationTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegrationTaskApplication.class, args);
    }

    // configuration for swagger api
    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.application.integration_task"))
                .build()
                .apiInfo(apiDetails());
    }

    // custom doc info
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Integration Task REST API",
                "REST API for Beneficiary CRUD Operations and Getting QR Codes",
                null,
                null,
                new Contact(
                        "Silvestras Bumblauskas",
                        "https://github.com/silvestrasb",
                        "silvestras.bumblauskas@gmail.com"
                ),
                null,
                null,
                Collections.emptyList()
        );
    }
}
