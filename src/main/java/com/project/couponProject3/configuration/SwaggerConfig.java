package com.project.couponProject3.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
/**
 * Class of Swagger Configuration
 */
public class SwaggerConfig {

    @Bean
    /**
     * Configuration of swagger
     */
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2) //return information in swagger 2 format
                .select() //select the documentation type
                .apis(RequestHandlerSelectors.any()) //use any selectors
                .paths(PathSelectors.any())          //use any path
                .build();                            //build our new swagger
    }

}
