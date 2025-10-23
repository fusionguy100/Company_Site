package com.fasttrack.greenteam.GroupFinal.mappers;

import java.util.List;

import com.fasttrack.greenteam.GroupFinal.dtos.*;
import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import org.mapstruct.Mapper;

import com.fasttrack.greenteam.GroupFinal.entities.Company;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target="users", source="users", qualifiedByName="mapCompanyUsersToSummaries")
    @Mapping(target="teams", source="teams", qualifiedByName="mapCompanyTeamsToSummaries")
    @Mapping(target="announcements", source="announcements", qualifiedByName="mapCompanyAnnouncementsToSummaries")
    CompanyResponseDto entityToDto(Company company);
    Company dtoToEntity(CompanyRequestDto companyRequestDto);
    List<CompanyResponseDto> entitiesToDtos(List<Company> companies);

    @Named("mapCompanyUsersToSummaries")
    default List<UserSummaryDto> mapCompanyUsersToSummaries(List<User> users) {
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
    @Named("mapCompanyTeamsToSummaries")
    default List<TeamSummaryDto> mapCompanyTeamsToSummaries(List<Team> teams) {
        if (teams == null) return null;
        return teams.stream()
                .map(team ->
                        {
                            TeamSummaryDto dto = new TeamSummaryDto();
                            dto.setId(team.getId());
                            dto.setName(team.getName());
                            dto.setDescription(team.getDescription());
                            return dto;
                        }
                )
                .toList();
    }
    @Named("mapCompanyAnnouncementsToSummaries")
    default List<AnnouncementSummaryDto> mapCompanyAnnouncementsToSummaries(List<Announcement> announcements) {
        if (announcements == null) return null;
        return announcements.stream()
                .map(announcement -> {
                    String authorUsername = announcement.getAuthor() != null
                            && announcement.getAuthor().getCredentials() != null
                            ? announcement.getAuthor().getCredentials().getUsername()
                            : "Unknown";
                    AnnouncementSummaryDto dto = new AnnouncementSummaryDto();
                    dto.setId(announcement.getId());
                    dto.setDate(announcement.getDate());
                    dto.setTitle(announcement.getTitle());
                    dto.setAuthorUsername(authorUsername);

                    return dto;
                })
                .toList();
    }
}
