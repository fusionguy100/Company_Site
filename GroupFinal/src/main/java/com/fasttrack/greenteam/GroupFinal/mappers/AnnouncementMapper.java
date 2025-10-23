package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.*;
import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
@Mapper(componentModel = "spring", uses = { CompanyMapper.class })
public interface AnnouncementMapper {

    // Entity -> DTO
    @Mapping(target = "author", source = "author", qualifiedByName = "userToSummary")
    AnnouncementResponseDto entityToDto(Announcement a);

    List<AnnouncementResponseDto> entitiesToDtos(List<Announcement> list);

    // Request -> Entity (relations/date set in service)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "author",  ignore = true)
    @Mapping(target = "date",    ignore = true)
    Announcement dtoToEntity(AnnouncementRequestDto dto);

    @Named("userToSummary")
    static UserSummaryDto userToSummary(User u) {
        if (u == null) return null;
        var dto = new UserSummaryDto();
        dto.setId(u.getId());
        if (u.getCredentials() != null) dto.setUsername(u.getCredentials().getUsername());
        if (u.getProfile() != null) {
            dto.setFirstName(u.getProfile().getFirst());
            dto.setLastName(u.getProfile().getLast());
        }
        return dto;
    }

}
