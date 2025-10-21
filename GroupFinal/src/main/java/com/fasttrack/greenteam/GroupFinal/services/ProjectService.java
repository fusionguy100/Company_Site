package com.fasttrack.greenteam.GroupFinal.services;

import com.fasttrack.greenteam.GroupFinal.dtos.ProjectRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;

import java.util.List;

public interface ProjectService {
    List<ProjectResponseDto> getProjects();

    ProjectResponseDto createProject(ProjectRequestDto projectRequestDto);

    ProjectResponseDto getProject(Long id);

    ProjectResponseDto updateProject(ProjectRequestDto projectRequestDto, Long id);

    ProjectResponseDto inactivateProject(Long id);
}
