package com.fasttrack.greenteam.GroupFinal.services.impls;

import com.fasttrack.greenteam.GroupFinal.dtos.ProjectRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.Project;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotFoundException;
import com.fasttrack.greenteam.GroupFinal.mappers.ProjectMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.ProjectRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.TeamRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.ProjectService;
import com.fasttrack.greenteam.GroupFinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TeamRepository teamRepository;


    Project findProject(Long id) {
        Optional<Project> project = projectRepository.findProjectById(id);
        if(project.isEmpty()) {
            throw new NotFoundException("Project not found");
        }
        return project.get();
    }

    Team findTeam(Long id){
        Optional<Team> team = teamRepository.findById(id);
        if(team.isEmpty()) {
            throw new NotFoundException("Team not found");
        }
        return team.get();
    }

    @Override
    public List<ProjectResponseDto> getProjects() {
        return projectMapper.entitiesToDtos(projectRepository.findAll());
    }

    @Override
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        Project project = projectMapper.dtoToEntity(projectRequestDto);

        if (projectRequestDto.getTeam() == null) {
            throw new IllegalArgumentException("team id is required");
        }
        Team teamRef = teamRepository.getReferenceById(projectRequestDto.getTeam());
        project.setTeam(teamRef);
        Project savedProject = projectRepository.saveAndFlush(project);
        return projectMapper.entityToDto(savedProject);
    }

    @Override
    public ProjectResponseDto getProject(Long id) {
        return projectMapper.entityToDto(findProject(id));
    }

    @Override
    public ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long id) {
        Project project = findProject(id);
        if (projectRequestDto.getName() != null && !projectRequestDto.getName().isBlank()) {
            project.setName(projectRequestDto.getName().trim());
        }
        if (projectRequestDto.getDescription() != null && !projectRequestDto.getDescription().isBlank()) {
            project.setDescription(projectRequestDto.getDescription().trim());
        }
        if (projectRequestDto.getActive() != null) {
            project.setActive(projectRequestDto.getActive());
        }
        if (projectRequestDto.getTeam() != null) {
            Team team = findTeam(projectRequestDto.getTeam());
            project.setTeam(team);
        }

        Project saved = projectRepository.saveAndFlush(project);
        return projectMapper.entityToDto(saved);
    }

    @Override
    public ProjectResponseDto inactivateProject(Long id) {
        Project project = findProject(id);
        project.setActive(false);
        return projectMapper.entityToDto(projectRepository.saveAndFlush(project));
    }
}
