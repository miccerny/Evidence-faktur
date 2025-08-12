package cz.itnetwork.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for the application.
 *
 * <p>Implements {@link WebMvcConfigurer} to customize Spring MVC settings.
 * In this case, it configures Cross-Origin Resource Sharing (CORS) rules
 * so the frontend can communicate with the backend.</p>
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Configures the application's CORS (Cross-Origin Resource Sharing) rules.
     *
     * <p>Allows requests from the specified origin (<code>http://localhost:3000</code>)
     * to access all backend endpoints. It also defines the allowed HTTP methods
     * and enables sending cookies and authentication headers.</p>
     *
     * @param registry the registry to configure CORS mappings
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
