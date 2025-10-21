package com.fasttrack.greenteam.GroupFinal.services.impls;


import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;
import com.fasttrack.greenteam.GroupFinal.dtos.TeamRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.TeamResponseDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Project;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.exceptions.BadRequestException;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotAuthorizedException;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotFoundException;
import com.fasttrack.greenteam.GroupFinal.mappers.ProjectMapper;
import com.fasttrack.greenteam.GroupFinal.mappers.TeamMapper;
import com.fasttrack.greenteam.GroupFinal.mappers.UserMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.TeamRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    @Override
    public List<TeamResponseDto> getTeams() {
        return teamMapper.entitiesToDtos(teamRepository.findAll());
    }

    @Override
    public TeamResponseDto createTeam(Long userId, TeamRequestDto teamRequestDto) {
        //for now without Spring Security
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
        if(!user.getAdmin()) {
            throw new NotAuthorizedException("Only an admin can create a new teams!");
        }
        validateTeam(teamRequestDto);

        Team team = teamMapper.dtoToEntity(teamRequestDto);
        Team savedTeam = teamRepository.saveAndFlush(team);
        return teamMapper.entityToDto(savedTeam);

    }

    @Override
    public TeamResponseDto getTeamByID(Long id) {
        if (id == null) {
            throw new BadRequestException("Team ID cannot be null!");
        }

        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Team with ID " + id + " not found"));

        return teamMapper.entityToDto(team);

    }

    @Override
    public TeamResponseDto addUserToTeam(Long teamId, Long userId, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!currentUser.getAdmin()) {
            throw new NotAuthorizedException("You must be an admin to perform this action");
        }

        if (teamId == null || userId == null) {
            throw new BadRequestException("Team ID and User ID cannot be null!");
        }
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("No team found with that id!"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user found with that id!"));

        if(!team.getUsers().contains(user))
            team.getUsers().add(user);

        if(!user.getTeams().contains(team))
            user.getTeams().add(team);

        teamRepository.saveAndFlush(team);
        userRepository.saveAndFlush(user);

        return teamMapper.entityToDto(team);
    }

    @Override
    public UserResponseDto removeUserFromTeam(Long teamId, Long userId) {

        if (teamId == null || userId == null) {
            throw new BadRequestException("Team ID and User ID cannot be null!");
        }
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("No team found with that id! "));


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user found with that id!"));

        if (!team.getUsers().contains(user)) {
            throw new BadRequestException("User is not part of this team!");
        }

        User preDelete = user;

        team.getUsers().remove(user);
        user.getTeams().remove(team);

        teamRepository.saveAndFlush(team);
        userRepository.saveAndFlush(user);

        return userMapper.entityToDto(preDelete);

    }

    @Override
    public List<ProjectResponseDto> getAllProjects(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("Team id cannot be null!");
        }
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("That team does not exist!"));
        if (team.getProjects().isEmpty()) {
            throw new NotFoundException("This team does not have any active projects!");
        }
        return projectMapper.entitiesToDtos(new ArrayList<>(team.getProjects()));

    }

    @Override
    public List<UserResponseDto> getAllUsers(Long teamId) {
        if (teamId == null) {
            throw new BadRequestException("Team id cannot be null!");
        }
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("That team does not exist!"));

        if (team.getUsers().isEmpty()) {
            throw new NotFoundException("This team does not have any users!");
        }
        return userMapper.entitiesToDtos(new ArrayList<>(team.getUsers()));
    }

    @Override
    public TeamResponseDto updateTeam(Long teamId, TeamRequestDto teamRequestDto) {
        if (teamId == null) {
            throw new BadRequestException("Team ID cannot be null!");
        }
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() ->new NotFoundException("Team not found with this ID!"));

        boolean updated = false;

        if (teamRequestDto.getName() != null && !teamRequestDto.getName().isBlank()) {
            team.setName(teamRequestDto.getName());
            updated = true;
        }

        if (teamRequestDto.getDescription() != null && !teamRequestDto.getDescription().isBlank()) {
            team.setDescription(teamRequestDto.getDescription());
            updated = true;
        }

        if (!updated) {
            throw new BadRequestException("No valid fields provided for update!");
        }

        Team savedTeam = teamRepository.saveAndFlush(team);
        return teamMapper.entityToDto(savedTeam);

    }


    //helper method
    public boolean validateTeam(TeamRequestDto teamRequestDto) {
        if (teamRequestDto == null ) throw new BadRequestException("Team needs to be non empty!");
        if (teamRequestDto.getName().isEmpty() || teamRequestDto.getName().isBlank()) throw new BadRequestException("Team needs to have a name!");
        if (teamRequestDto.getCompany() == null) throw new BadRequestException("Each team needs a required company!");
        return true;
    }

}