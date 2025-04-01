// config file to allow frontend to make API requests to the backend

package com.url_shortener.url_shortener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow requests from localhost:5173 (React frontend)
        registry.addMapping("/**")  // Adjust to match your API endpoint pattern
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow specific methods
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}

