package cz.itnetwork.controller;

import cz.itnetwork.dto.UserDTO;
import cz.itnetwork.entity.UserEntity;
import cz.itnetwork.entity.repository.UserRepository;
import cz.itnetwork.exceptions.BadPasswordException;
import cz.itnetwork.exceptions.EmailNotFoundException;
import cz.itnetwork.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller that provides API endpoints for managing users and authentication.
 *
 * <p>All requests related to users start with the path {@code /api}.
 * This controller handles operations such as user registration, login, logout,
 * and retrieving the currently authenticated user.</p>
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user.
     *
     * @param userDTO the DTO containing user registration details
     * @return the created user as a DTO
     */
    @PostMapping({"/user", "/user/"})
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.create(userDTO);
    }

    /**
     * Authenticates a user and starts a session.
     *
     * @param userDTO the DTO containing login credentials (email and password)
     * @param request the HTTP request used to create the session
     * @return a map containing login success message, user details, and authorities
     * @throws EmailNotFoundException if no user with the given email exists
     * @throws BadPasswordException   if the provided password is incorrect
     */
    @PostMapping({"/auth", "/auth/"})
    public Map<String, Object> login(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) {
        UserEntity user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("User with this email does not exist"));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new BadPasswordException("Incorrect password");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        request.getSession(true);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("email", user.getEmail());
        response.put("userId", user.getUserId());
        response.put("admin", user.isAdmin());
        response.put("authorities", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return response;
    }

    /**
     * Logs out the currently authenticated user.
     *
     * @param req the HTTP request used to terminate the session
     * @return a map containing the logout message
     * @throws ServletException if an error occurs during logout
     */
    @DeleteMapping({"/logout", "/logout/"})
    public Map<String, String> logout(HttpServletRequest req) throws ServletException {
        req.logout();
        return Map.of("message", "User logged out");
    }

    /**
     * Returns details of the currently authenticated user.
     *
     * @return a map containing the user's email, ID, admin status, and authorities
     * @throws ResponseStatusException with status 401 if the user is not authenticated
     */
    @GetMapping("/auth")
    public Map<String, Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        UserEntity user = (UserEntity) authentication.getPrincipal();

        Map<String, Object> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("userId", user.getUserId());
        response.put("admin", user.isAdmin());
        response.put("authorities", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return response;
    }
}
