package cz.itnetwork.service;

import cz.itnetwork.dto.UserDTO;
import cz.itnetwork.entity.UserEntity;
import cz.itnetwork.entity.repository.UserRepository;
import cz.itnetwork.exceptions.DuplicateEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing users.
 * <p>
 * Provides methods for creating new users and loading user details
 * for authentication purposes.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates a new user in the system.
     * <p>
     * This method:
     * <ul>
     *   <li>Checks if the email is already in use.</li>
     *   <li>Encodes the password for secure storage.</li>
     *   <li>Saves the user to the database.</li>
     *   <li>Returns a {@link UserDTO} containing the created user's details.</li>
     * </ul>
     *
     * @param model DTO containing the new user's data
     * @return the created user as {@link UserDTO}
     * @throws DuplicateEmailException if the provided email already exists
     */
    @Override
    public UserDTO create(UserDTO model) {
        checkDuplicateEmail(model);

        UserEntity entity = new UserEntity();
        entity.setEmail(model.getEmail());
        entity.setPassword(passwordEncoder.encode(model.getPassword()));

        entity = userRepository.save(entity);

        UserDTO dto = new UserDTO();
        dto.setUserId(entity.getUserId());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    /**
     * Loads a user's details by their email address for authentication.
     *
     * @param username the email of the user to load
     * @return the {@link UserDetails} for authentication
     * @throws UsernameNotFoundException if no user is found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username '" + username + "' not found"));
    }

    /**
     * Checks if a user with the given email already exists.
     *
     * @param userDTO the user data to check
     * @throws DuplicateEmailException if the email already exists
     */
    private void checkDuplicateEmail(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateEmailException(userDTO.getEmail());
        }
    }
}
