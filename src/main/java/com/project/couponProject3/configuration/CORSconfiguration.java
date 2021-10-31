package com.project.couponProject3.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
/**
 * Class of CORS configuration
 */
public class CORSconfiguration {

    @Bean
    /**
     * Configuration of CORS
     */
    public CorsFilter corsFilter(){
        //create new url configuration for browsers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //create new cors configuration....
        CorsConfiguration config = new CorsConfiguration();
        //allow to get credentials in cors
        config.setAllowCredentials(true);
        //allow to get from any ip/domain
//        config.addAllowedOrigin("*");
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        //allow to get any header
        config.addAllowedHeader("*");

//        config.addExposedHeader("*"); ==>> check if I get token in header->authorization, if not add this expose

        //allow to get methods
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        //allow to get any route -> localhost:8080/api/lecturer -> /api/lecture is route
        source.registerCorsConfiguration("/**",config);
        config.addExposedHeader("*");
        //return new configuration
        return new CorsFilter(source);
    }

}
