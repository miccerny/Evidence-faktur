package cz.itnetwork.service;


import cz.itnetwork.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service interface for managing users and authentication.
 * <p>
 * Extends UserDetailsService to support Spring Security login.
 */
public interface UserService extends UserDetailsService {

    /**
     * Creates a new user based on the provided data.
     *
     * @param model - the user data as a DTO
     * @return the created user as a DTO
     */
    UserDTO create(UserDTO model);
}
