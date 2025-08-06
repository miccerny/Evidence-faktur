package cz.itnetwork.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up the web layer of the application.
 * <p>
 * Activates and customizes Spring MVC configuration
 * using the {@link WebMvcConfigurer} interface implementation.
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Configures CORS (Cross-Origin Resource Sharing) rules for the application.
     * <p>
     * Allows access from all origins (specified in .allowedOrigins)
     * for all URL paths. Sets allowed HTTP methods and enables sending cookies.
     *
     * @param registry the CORS registry to configure rules
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins("http://localhost:3000")
                .allowCredentials(true);
    }
}
