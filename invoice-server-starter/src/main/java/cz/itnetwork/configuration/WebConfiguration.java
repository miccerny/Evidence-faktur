package cz.itnetwork.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Konfigurační třída pro nastavení webové vrstvy aplikace.
 * *
 * Aktivuje a přizpůsobuje konfiguraci Spring MVC
 * pomocí implementace rozhraní {@link WebMvcConfigurer}.
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Konfiguruje pravidla CORS (Cross-Origin Resource Sharing) pro aplikaci.
     * *
     * Povolení přístupu ze všech zdrojů (origin) pro všechny URL cesty.
     * Nastavuje povolené HTTP metody a umožňuje zasílání cookies.
     *
     * @param registry registr CORS pravidel
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOriginPatterns("**")
                .allowCredentials(true);
    }
}
