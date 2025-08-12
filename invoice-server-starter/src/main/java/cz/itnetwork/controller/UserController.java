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

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping({"/user", "/user/"})
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PostMapping({"/auth","/auth/"})
    public Map<String, Object> login(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) {
        System.out.println("📥 LOGIN ENDPOINT ZASAŽEN!");
        System.out.println("Přišly údaje: " + userDTO.getEmail());

        UserEntity user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("Uživatel s tímto e-mailem neexistuje"));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new BadPasswordException("Nesprávné heslo");
        }


        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        request.getSession(true);
        

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Přihlášení proběhlo úspěšně");
        response.put("email", user.getEmail());
        response.put("userId", user.getUserId());
        response.put("admin", user.isAdmin());
        response.put("authorities", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return response;
    }

    @DeleteMapping({"/logout", "/logout/"})
    public Map<String, String> logout(HttpServletRequest req) throws ServletException {
        req.logout();
        return Map.of("message", "Uživatel odhlášen");
    }

    @GetMapping("/auth")
    public Map<String, Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Uživatel není přihlášen");
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
