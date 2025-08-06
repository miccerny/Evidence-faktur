package cz.itnetwork.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * ApplicationSecurityConfiguration sets up security for the Spring Boot application.
 *
 * - Configures how passwords are stored and checked.
 * - Defines which API endpoints are public and which require login.
 * - Disables the default HTML login form, since this app uses its own authentication (e.g., via API).
 * - Sets up custom logout handling and session rules.
 *
 * This configuration is suitable for applications with a frontend (like React)
 * that communicates with this backend via HTTP requests.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ApplicationSecurityConfiguration {

    /**
     * Password encoder bean.
     *
     * This bean uses BCrypt to safely hash user passwords before storing them in the database.
     * BCrypt is a widely-used hashing algorithm designed for security.
     *
     * @return PasswordEncoder object for hashing passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the HTTP security for the application.
     *
     * - Disables CSRF (useful for APIs).
     * - Allows all requests to /api/** without authentication.
     * - Requires authentication for all other requests.
     * - Disables the default HTML form login.
     * - Sets a custom logout URL (/api/logout).
     * - Configures session management to only create a session if required.
     *
     * @param httpSecurity the security configuration object
     * @return SecurityFilterChain the configured security filter chain
     * @throws Exception if any security configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.cors(cors -> {})
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form.disable()) // nemáme vlastní HTML login formulář
                .logout(logout -> logout.logoutUrl("/api/logout").logoutSuccessHandler((req, res, auth) -> {}))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
                return httpSecurity.build();
    }
}
