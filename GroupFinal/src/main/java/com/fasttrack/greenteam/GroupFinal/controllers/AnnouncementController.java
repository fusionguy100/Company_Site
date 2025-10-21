package com.fasttrack.greenteam.GroupFinal.controllers;

import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementResponseDto;
import com.fasttrack.greenteam.GroupFinal.services.AnnouncementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/announcements")
@AllArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping
    public List<AnnouncementResponseDto> getAnnouncements(){ return announcementService.getAnnouncements();}

    @PostMapping
    public AnnouncementResponseDto createAnnouncement(@RequestBody AnnouncementRequestDto announcementRequestDto){return announcementService.createAnnouncement(announcementRequestDto);}

    @GetMapping("/{id}")
    public AnnouncementResponseDto getAnnouncement(@PathVariable Long id){ return announcementService.getAnnouncement(id);}

    @PatchMapping("/{id}")
    public AnnouncementResponseDto updateAnnouncement(@RequestBody AnnouncementRequestDto announcementRequestDto, @PathVariable Long id){ return announcementService.updateAnnouncement(announcementRequestDto, id);}

}
