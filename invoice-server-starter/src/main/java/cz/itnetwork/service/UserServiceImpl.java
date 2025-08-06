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
 * Implementation of the user service for managing users and authentication.
 * <p>
 * Contains logic for creating new users and loading users for login.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates a new user based on the provided data.
     * <p>
     * Checks for duplicate email, encodes the password,
     * saves the user to the database, and returns user info as a DTO.
     *
     * @param model the user data as a DTO
     * @return the created user as a DTO
     */
    @Override
    public UserDTO create(UserDTO model) {

        checkDuplicateEmail(model); // Throws exception if email is already used
        UserEntity entity = new UserEntity();
        entity.setEmail(model.getEmail());
        entity.setPassword(passwordEncoder.encode(model.getPassword())); // Store encrypted password

        entity = userRepository.save(entity);

        UserDTO dto = new UserDTO();
        dto.setUserId(entity.getUserId());
        // (Optional) Typically, you would not return the password in a DTO!
        dto.setPassword(entity.getEmail());
        return dto;
    }

    /**
     * Loads a user by username (email) for Spring Security login.
     * <p>
     * Finds the user in the database. If not found, throws UsernameNotFoundException.
     *
     * @param username the user's email (used as username)
     * @return the user as a UserDetails object
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found"));
    }

    /**
     * Checks if a user with the given email already exists.
     * <p>
     * Throws DuplicateEmailException if the email is already in use.
     *
     * @param userDTO user data to check
     */
    private void checkDuplicateEmail(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateEmailException(userDTO.getEmail());
        }
    }
}
