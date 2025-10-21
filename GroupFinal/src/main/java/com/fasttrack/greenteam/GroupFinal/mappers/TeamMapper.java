package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.TeamRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.TeamResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface TeamMapper {

    TeamResponseDto entityToDto(Team team);
    List<TeamResponseDto> entitiesToDtos(List<Team> teams);
    Team dtoToEntity(TeamRequestDto teamRequestDto);

}
