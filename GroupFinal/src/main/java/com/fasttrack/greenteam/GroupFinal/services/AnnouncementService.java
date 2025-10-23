package com.fasttrack.greenteam.GroupFinal.services;

import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementResponseDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface AnnouncementService {
    List<AnnouncementResponseDto> getAnnouncements();

    AnnouncementResponseDto createAnnouncement(AnnouncementRequestDto announcementRequestDto, HttpSession session);

    AnnouncementResponseDto getAnnouncement(Long id);

    AnnouncementResponseDto updateAnnouncement(AnnouncementRequestDto announcementRequestDto, Long id);
}
