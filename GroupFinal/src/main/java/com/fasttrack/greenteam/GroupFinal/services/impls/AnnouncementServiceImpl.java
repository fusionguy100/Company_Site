package com.fasttrack.greenteam.GroupFinal.services.impls;

import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import com.fasttrack.greenteam.GroupFinal.entities.Project;
import com.fasttrack.greenteam.GroupFinal.exceptions.BadRequestException;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotFoundException;
import com.fasttrack.greenteam.GroupFinal.mappers.AnnouncementMapper;
import com.fasttrack.greenteam.GroupFinal.mappers.ProjectMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.AnnouncementRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.ProjectRepository;
import com.fasttrack.greenteam.GroupFinal.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;

    Announcement findAnnouncement(Long id) {
        Optional<Announcement> announcement = announcementRepository.findAnnouncementById(id);
        if(announcement.isEmpty()) {
            throw new NotFoundException("Announcement not found");
        }
        return announcement.get();
    }

    @Override
    public List<AnnouncementResponseDto> getAnnouncements() {
        return announcementMapper.entityToDtos(announcementRepository.findAll());
    }

    @Override
    public AnnouncementResponseDto createAnnouncement(AnnouncementRequestDto announcementRequestDto) {
        return announcementMapper.entityToDto(announcementRepository.saveAndFlush(announcementMapper.dtoToEntity(announcementRequestDto)));
    }

    @Override
    public AnnouncementResponseDto getAnnouncement(Long id) {
        Announcement announcement = findAnnouncement(id);
        return announcementMapper.entityToDto(announcement);
    }

    @Override
    public AnnouncementResponseDto updateAnnouncement(AnnouncementRequestDto announcementRequestDto, Long id) {
        Announcement announcement = findAnnouncement(id);
        boolean touched = false;

        if (announcementRequestDto.getTitle() != null && !announcementRequestDto.getTitle().isBlank()) {
            announcement.setTitle(announcementRequestDto.getTitle().trim());
            touched = true;
        }
        if (announcementRequestDto.getMessage() != null && !announcementRequestDto.getMessage().isBlank()) {
            announcement.setMessage(announcementRequestDto.getMessage().trim());
            touched = true;
        }
        if (announcementRequestDto.getCompany() != null) {
            throw new BadRequestException("Company cannot be changed via PATCH /announcements/{id}");
        }
        if (!touched) {
            throw new BadRequestException("Nothing to update. Provide title and/or message.");
        }

        Announcement saved = announcementRepository.saveAndFlush(announcement);
        return announcementMapper.entityToDto(saved);
    }
}
