package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.ProjectRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Project;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses ={TeamMapper.class})
public interface ProjectMapper {

    @Mapping(target = "team", source = "team")
    ProjectResponseDto entityToDto(Project project);
    List<ProjectResponseDto> entitiesToDtos(List<Project> projects);

    @Mapping(target = "team", source = "team", qualifiedByName = "mapTeamIdToEntity")
    Project dtoToEntity(ProjectRequestDto projectRequestDto);



    @Named("mapTeamIdToEntity")
    default Team mapTeamIdToEntity(Long teamId) {
        if (teamId == null) return null;
        Team team = new Team();
        team.setId(teamId);
        return team;
    }
}
