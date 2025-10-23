package com.fasttrack.greenteam.GroupFinal.controllers;

import com.fasttrack.greenteam.GroupFinal.dtos.*;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotAuthorizedException;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotFoundException;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/teams")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TeamController {

    private final TeamService teamService;
    private final UserRepository userRepository;

    //  Everyone can view teams
    @GetMapping
    public List<TeamResponseDto> getTeams() {
        return teamService.getTeams();
    }

    //  Only admin can create teams
    @PostMapping
    public TeamResponseDto createTeam(@RequestBody TeamRequestDto teamRequestDto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) throw new NotAuthorizedException("You must be logged in");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getAdmin())
            throw new NotAuthorizedException("Only admins can create new teams!");

        return teamService.createTeam(userId, teamRequestDto);
    }

    @PostMapping("/{teamId}/users/{userId}")
    public TeamResponseDto addUserToTeam(@PathVariable Long teamId,
                                         @PathVariable Long userId,
                                         HttpSession session) {
        Long currentUserId = (Long) session.getAttribute("userId");
        if (currentUserId == null) throw new NotAuthorizedException("You must be logged in");

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!currentUser.getAdmin())
            throw new NotAuthorizedException("Only admins can add users to teams!");

        return teamService.addUserToTeam(teamId, userId, currentUserId);
    }

    //  Only admin can remove users
    @DeleteMapping("/{teamId}/users/{userId}")
    public TeamResponseDto removeUserFromTeam(@PathVariable Long teamId,
                                              @PathVariable Long userId,
                                              HttpSession session) {
        Long currentUserId = (Long) session.getAttribute("userId");
        if (currentUserId == null) throw new NotAuthorizedException("You must be logged in");

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!currentUser.getAdmin())
            throw new NotAuthorizedException("Only admins can remove users from teams!");

        return teamService.removeUserFromTeam(teamId, userId);
    }

    // Only admin can update a team
    @PatchMapping("/{teamId}")
    public TeamResponseDto updateTeam(
            @PathVariable Long teamId,
            @RequestBody TeamRequestDto teamRequestDto,
            HttpSession session) {

        Long currentUserId = (Long) session.getAttribute("userId");
        if (currentUserId == null) {
            throw new NotAuthorizedException("You must be logged in!");
        }

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        if (!Boolean.TRUE.equals(currentUser.getAdmin())) {
            throw new NotAuthorizedException("Only admins can update teams!");
        }

        return teamService.updateTeam(teamId, teamRequestDto);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new NotAuthorizedException("You must be logged in");
        }

        teamService.deleteTeam(teamId, userId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public TeamResponseDto getTeamById(@PathVariable Long id) {
        return teamService.getTeamByID(id);
    }

    @GetMapping("/{teamId}/projects")
    public List<ProjectResponseDto> getAllProjects(@PathVariable Long teamId) {
        return teamService.getAllProjects(teamId);
    }

    @GetMapping("/{teamId}/users")
    public List<UserResponseDto> getAllUsers(@PathVariable Long teamId) {
        return teamService.getAllUsers(teamId);
    }
}
