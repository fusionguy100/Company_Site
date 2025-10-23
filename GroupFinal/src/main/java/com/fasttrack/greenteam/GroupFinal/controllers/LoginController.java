package com.fasttrack.greenteam.GroupFinal.controllers;

import com.fasttrack.greenteam.GroupFinal.dtos.CredentialsDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.mappers.UserMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoginController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public LoginController(UserService userService, UserMapper userMapper, UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(HttpSession session,
                                                 @RequestBody CredentialsDto credentials) {

        User user = userService.validateCredentials(credentials.getUsername(), credentials.getPassword());

        System.out.println("Login attempt for user: " + credentials.getUsername());
        if (user == null) {
            System.out.println("Login failed for user: " + credentials.getUsername());
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("isAdmin", user.getAdmin());
        user.setActive(Boolean.TRUE);
        user.setStatus("JOINED");
        userRepository.save(user);
        return ResponseEntity.ok(userMapper.entityToDto(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/currentUser")
    public ResponseEntity<UserResponseDto> getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
        UserResponseDto userResponseDto = userService.getUserById(userId);
        return ResponseEntity.ok(userResponseDto);
    }
}

