package com.fasttrack.greenteam.GroupFinal.controllers;

import com.fasttrack.greenteam.GroupFinal.dtos.CredentialsDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotAuthorizedException;
import com.fasttrack.greenteam.GroupFinal.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public List<UserResponseDto> getUsers() {
        return userService.getAllUsers();
    }
    @PostMapping()
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto, HttpSession session) {
        if (session.getAttribute("userId") == null || session.getAttribute("isAdmin") == null || !(Boolean) session.getAttribute("isAdmin")) {
            throw new NotAuthorizedException("Only admins can create new users.");
        }

        return userService.createUser(userRequestDto);
    }
    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @PatchMapping("/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(id, userRequestDto);
    }
    @DeleteMapping("/{id}")
    public UserResponseDto deleteUser(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        return userService.deleteUser(id);
    }
//    @GetMapping("/{id}/companies")
//    public UserResponseDto getUserCompanies(@PathVariable Long id) {
//        return userService.getUserCompanies(id);
//    }
//    @GetMapping("/{id}/teams")
//    public UserResponseDto getUserTeams(@PathVariable Long id) {
//        return userService.getUserTeams(id);
//    }

}
