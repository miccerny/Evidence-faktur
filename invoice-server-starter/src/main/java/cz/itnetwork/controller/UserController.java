package cz.itnetwork.controller;

import cz.itnetwork.dto.UserDTO;
import cz.itnetwork.entity.UserEntity;
import cz.itnetwork.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping({"/user", "/user/"})
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO){
        return userService.create(userDTO);
    }

    @PostMapping({"/auth", "/auth/"})
    public UserDTO login(@RequestBody @Valid UserDTO userDTO, HttpServletRequest req) throws ServletException {
        req.login(userDTO.getEmail(), userDTO.getPassword());

        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO model = new UserDTO();
        model.setEmail(user.getEmail());
        model.setUserId(user.getUserId());
        model.setAdmin(user.isAdmin());
        return model;
    }

    @DeleteMapping({"/auth", "/auth/"})
    public String logout(HttpServletRequest req) throws ServletException {
        req.logout();
        return "Uživatel odhlášen";
    }

    @GetMapping("/auth")
    public UserDTO getCurrentUser() throws ServletException {
        try {
            UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            UserDTO model = new UserDTO();
            model.setEmail(user.getEmail());
            model.setUserId(user.getUserId());
            model.setAdmin(user.isAdmin());
            return model;
        } catch (ClassCastException e) {
            throw new ServletException();
        }
    }
}
