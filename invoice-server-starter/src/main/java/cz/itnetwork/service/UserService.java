package cz.itnetwork.service;

import cz.itnetwork.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service interface for managing users.
 * <p>
 * Extends {@link UserDetailsService} to integrate with Spring Security
 * for loading user details during authentication.
 */
public interface UserService extends UserDetailsService {

    /**
     * Creates a new user.
     *
     * @param model DTO containing the new user's data
     * @return the created user as a {@link UserDTO}
     */
    UserDTO create(UserDTO model);
}

