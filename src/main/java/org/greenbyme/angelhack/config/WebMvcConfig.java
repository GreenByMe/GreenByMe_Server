package org.greenbyme.angelhack.config;

import org.greenbyme.angelhack.auth.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] EXCLUDE_PATHS = {
            "/api/users/signin",
            "/api/users/signin/social",
            "/api/users/signup",
            "/api/users/signup/social",
            "/api/users/images/{fileName}",
            "/api/users/email/**",
            "/api/users/nickname/**",
            "/api/missions/**",
            "/api/categorys/**",
            "/api/posts/images/{fileName}",
            "/api/posts/missions/**",
            "/api/mail/**"
            };

    private final long MAX_AGE_SECS = 3600;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(EXCLUDE_PATHS);
    }
}
