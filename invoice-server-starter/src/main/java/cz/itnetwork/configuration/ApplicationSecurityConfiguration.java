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
 * Security configuration for the application.
 *
 * <p>This class configures Spring Security to handle authentication and authorization,
 * including CORS, CSRF, session management, and permitted endpoints.</p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ApplicationSecurityConfiguration {

    /**
     * Defines the password encoder bean used for hashing and verifying passwords.
     *
     * @return a BCrypt-based password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * <ul>
     *     <li>Enables CORS (with default configuration).</li>
     *     <li>Disables CSRF protection (since this is likely a stateless API).</li>
     *     <li>Permits all requests to endpoints under <code>/api/**</code> (currently open — may be restricted later).</li>
     *     <li>Requires authentication for all other endpoints.</li>
     *     <li>Disables the default HTML login form (assuming a token or API-based login).</li>
     *     <li>Configures logout at <code>/api/logout</code> without redirect handling.</li>
     *     <li>Uses <code>SessionCreationPolicy.IF_REQUIRED</code> — sessions will only be created if explicitly needed.</li>
     * </ul>
     *
     * @param httpSecurity the HttpSecurity instance to configure
     * @return the built SecurityFilterChain
     * @throws Exception if a security configuration error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.cors(cors -> {}) // allow Cross-Origin Resource Sharing
                .csrf(csrf-> csrf.disable()) // disable CSRF protection
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/**").permitAll() // currently permits all API endpoints
                        .anyRequest()
                        .authenticated()  // all other requests require authentication
                )
                .formLogin(form -> form.disable()) // disable HTML form login
                .logout(logout -> logout.logoutUrl("/api/logout").logoutSuccessHandler((req, res, auth) -> {})) // no redirect on logout
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
                return httpSecurity.build();
    }
}
