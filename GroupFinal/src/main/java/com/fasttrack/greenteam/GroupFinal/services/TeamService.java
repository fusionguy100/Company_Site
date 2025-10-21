package com.fasttrack.greenteam.GroupFinal.services;


import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;
import com.fasttrack.greenteam.GroupFinal.dtos.TeamRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.TeamResponseDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;

import java.util.List;

public interface TeamService {
    List<TeamResponseDto> getTeams();

    TeamResponseDto createTeam(Long userId, TeamRequestDto teamRequestDto);

    TeamResponseDto getTeamByID(Long id);

    TeamResponseDto addUserToTeam(Long teamId, Long userId, Long currentUserId);

    UserResponseDto removeUserFromTeam(Long teamId, Long userId);

    List<ProjectResponseDto> getAllProjects(Long teamId);

    List<UserResponseDto> getAllUsers(Long teamId);

    TeamResponseDto updateTeam(Long teamId, TeamRequestDto teamRequestDto);
}
