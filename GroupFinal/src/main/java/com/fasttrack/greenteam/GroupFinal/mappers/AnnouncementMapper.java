package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {

    AnnouncementResponseDto entityToDto(Announcement announcement);
    List<AnnouncementResponseDto> entityToDtos(List<Announcement> announcements);
    Announcement dtoToEntity(AnnouncementRequestDto announcementRequestDto);
}
