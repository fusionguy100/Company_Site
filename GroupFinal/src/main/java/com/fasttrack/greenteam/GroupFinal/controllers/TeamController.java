package com.fasttrack.greenteam.GroupFinal.controllers;


import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;
import com.fasttrack.greenteam.GroupFinal.dtos.TeamRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.TeamResponseDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;
import com.fasttrack.greenteam.GroupFinal.services.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;


    @GetMapping
    public List<TeamResponseDto> getTeams() {return teamService.getTeams();}

    @PostMapping
    public TeamResponseDto createTeam(@RequestParam Long userId, @RequestBody TeamRequestDto teamRequestDto) {
        return teamService.createTeam(userId, teamRequestDto);
    }

    @GetMapping("/{id}")
    public TeamResponseDto getTeamById(@PathVariable Long id) {
        return teamService.getTeamByID(id);
    }

    @PostMapping("/{teamId}/users/{userId}")
    public TeamResponseDto addUserToTeam(@PathVariable Long teamId, @PathVariable Long userId, @PathVariable Long currentUserId) {
        return teamService.addUserToTeam(teamId, userId, currentUserId);
    }

    @DeleteMapping("/{teamId}/users/{userId}")
    public UserResponseDto removeUserFromTeam(@PathVariable Long teamId, @PathVariable Long userId) {
        return teamService.removeUserFromTeam(teamId,userId);
    }

    @GetMapping("/{teamId}/projects")
    public List<ProjectResponseDto> getAllProjects (@PathVariable Long teamId) {
        return teamService.getAllProjects(teamId);
    }

    @GetMapping("/{teamId}/users")
    public List<UserResponseDto> getAllUsers(@PathVariable Long teamId) {
        return teamService.getAllUsers(teamId);
    }

    @PatchMapping("/teamId}")
    public TeamResponseDto updateTeam(@PathVariable Long teamId, @RequestBody TeamRequestDto teamRequestDto) {
        return teamService.updateTeam(teamId, teamRequestDto);
    }

}
