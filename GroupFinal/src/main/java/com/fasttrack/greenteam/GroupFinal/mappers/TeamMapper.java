package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.*;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface TeamMapper {

    @Mapping(target = "users", source = "users", qualifiedByName = "mapUsersToSummaries")
    @Mapping(target = "projects", source = "projects", qualifiedByName = "mapProjectsShallow")
    @Mapping(target = "company", source = "company", qualifiedByName = "mapCompanyToSummary")
    TeamResponseDto entityToDto(Team team);

    List<TeamResponseDto> entitiesToDtos(List<Team> teams);

    @Mapping(target = "company", ignore = true)
    Team dtoToEntity(TeamRequestDto teamRequestDto);

    @Named("mapUsersToSummaries")
    default List<UserSummaryDto> mapUsersToSummaries(List<User> users) {
        if (users == null) return null;
        return users.stream()
                .map(user -> {
                    UserSummaryDto dto = new UserSummaryDto();
                    dto.setId(user.getId());
                    if (user.getCredentials() != null) {
                        dto.setUsername(user.getCredentials().getUsername());
                    }
                    return dto;
                })
                .toList();
    }


    @Named("mapProjectsShallow")
    default List<ProjectSummaryDto> mapProjectsShallow(List<Project> projects) {
        if (projects == null) return null;
        return projects.stream()
                .map(p -> {
                    ProjectSummaryDto dto = new ProjectSummaryDto();
                    dto.setId(p.getId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setActive(p.isActive());
                    return dto;
                })
                .toList();
    }
    @Named("mapCompanyToSummary")
    default CompanySummaryDto mapCompanyToSummary(Company company) {
        if (company == null) return null;
        CompanySummaryDto dto = new CompanySummaryDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setDescription(company.getDescription());
        return dto;
    }
}
