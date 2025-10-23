package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.*;
import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class,
        CompanyMapper.class, TeamMapper.class})
public interface UserMapper {

    @Mapping(target="username", source="credentials.username")
    @Mapping(target="companies", source="companies", qualifiedByName="mapCompaniesToSummaries")
    @Mapping(target="teams", source="teams", qualifiedByName="mapTeamsToSummaries")
    @Mapping(target="announcements", source="announcements", qualifiedByName="mapAnnouncementsToSummaries")
    UserResponseDto entityToDto(User user);
    List<UserResponseDto> entitiesToDtos(List<User> users);
    @Mapping(target="admin", source="isAdmin")
    User dtoToEntity(UserRequestDto userRequestDto);

    @Named("mapUserToSummary")
    default UserSummaryDto mapUserToSummary(User user) {
        if (user == null) return null;
        UserSummaryDto dto = new UserSummaryDto();
        dto.setId(user.getId());
        if (user.getCredentials() != null) {
            dto.setUsername(user.getCredentials().getUsername());
        }
        if (user.getProfile() != null) {
            dto.setFirstName(user.getProfile().getFirst());
            dto.setLastName(user.getProfile().getLast());
        }
        dto.setAdmin(user.getAdmin() != null ? user.getAdmin() : false);
        dto.setActive(user.getActive() != null ? user.getActive() : false);
        return dto;
    }
    @Named("mapCompaniesToSummaries")
    default List<CompanySummaryDto> mapCompaniesToSummaries(List<Company> companies) {
        if (companies == null) return null;
        return companies.stream()
                .map(company -> new CompanySummaryDto(
                        company.getId(),
                        company.getName(),
                        company.getDescription()
                ))
                .toList();
    }
    @Named("mapTeamsToSummaries")
    default List<TeamSummaryDto> mapTeamsToSummaries(List<Team> teams) {
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
    @Named("mapAnnouncementsToSummaries")
    default List<AnnouncementSummaryDto> mapAnnouncementsToSummaries(List<Announcement> announcements) {
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
