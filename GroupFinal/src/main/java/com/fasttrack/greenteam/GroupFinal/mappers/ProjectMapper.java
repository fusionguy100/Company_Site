package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.ProjectRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponseDto entityToDto(Project project);
    List<ProjectResponseDto> entitiesToDtos(List<Project> projects);
    Project dtoToEntity(ProjectRequestDto projectRequestDto);
}
