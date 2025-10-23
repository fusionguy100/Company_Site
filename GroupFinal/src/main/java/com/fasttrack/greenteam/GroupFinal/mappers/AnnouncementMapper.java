package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementResponseDto;
import com.fasttrack.greenteam.GroupFinal.dtos.CompanySummaryDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserSummaryDto;
import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
@Mapper(componentModel = "spring", uses = { CompanyMapper.class })
public interface AnnouncementMapper {

    @Mapping(target = "company", source="company", qualifiedByName = "mapCompanyToSummary")
    @Mapping(target = "author", source="author", qualifiedByName = "mapUserToSummary")
    AnnouncementResponseDto entityToDto(Announcement announcement);
    List<AnnouncementResponseDto> entitiesToDtos(List<Announcement> announcements);
    @Mapping(target="company", ignore = true)
    Announcement dtoToEntity(AnnouncementRequestDto announcementRequestDto);

    @Named("mapCompanyToSummary")
    default CompanySummaryDto mapCompanyToSummary(Company company) {
        if (company == null) return null;
        CompanySummaryDto dto = new CompanySummaryDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setDescription(company.getDescription());
        return dto;
    }
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
}
