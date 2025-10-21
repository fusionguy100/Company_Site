package com.fasttrack.greenteam.GroupFinal.services;

import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementResponseDto;

import java.util.List;

public interface AnnouncementService {
    List<AnnouncementResponseDto> getAnnouncements();

    AnnouncementResponseDto createAnnouncement(AnnouncementRequestDto announcementRequestDto);

    AnnouncementResponseDto getAnnouncement(Long id);

    AnnouncementResponseDto updateAnnouncement(AnnouncementRequestDto announcementRequestDto, Long id);
}
