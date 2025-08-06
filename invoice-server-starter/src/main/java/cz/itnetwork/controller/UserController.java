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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller that provides API endpoints for user registration, authentication, and session management.
 *
 * All requests related to users start with "/api".
 * This controller handles user registration, login, logout, and fetching information about the currently logged-in user.
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
     * @param userDTO - an object containing user registration data
     * @return - the newly created user as a DTO
     */
    @PostMapping({"/user", "/user/"})
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.create(userDTO);
    }

    /**
     * Authenticates the user with the provided email and password.
     * If successful, sets up the user session and returns basic user information.
     *
     * @param userDTO - an object containing the email and password for login
     * @param request - the HTTP request object for session management
     * @return - a map with login message and user details
     */
    @PostMapping({"/auth","/auth/"})
    public Map<String, Object> login(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) {
        System.out.println("📥 LOGIN ENDPOINT HIT!");
        System.out.println("Received data: " + userDTO.getEmail());

        UserEntity user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("A user with this email does not exist"));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new BadPasswordException("Incorrect password");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        request.getSession(true);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

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
     * Logs out the current user.
     *
     * @param req - the HTTP request used to end the session
     * @return - a map with a logout message
     */
    @DeleteMapping({"/logout", "/logout/"})
    public Map<String, String> logout(HttpServletRequest req) throws ServletException {
        req.logout();
        return Map.of("message", "User logged out");
    }

    /**
     * Returns information about the currently logged-in user.
     * If no user is logged in, throws an unauthorized error.
     *
     * @return - a map with the user's email, ID, admin status, and authorities
     */
    @GetMapping("/auth")
    public Map<String, Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not logged in");
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
