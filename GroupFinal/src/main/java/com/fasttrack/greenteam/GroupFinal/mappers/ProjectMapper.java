package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.ProjectRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponseDto entityToDto(Project project);
    List<ProjectResponseDto> entitiesToDtos(List<Project> projects);
    @Mapping(target="team", ignore = true)
    Project dtoToEntity(ProjectRequestDto projectRequestDto);
}
