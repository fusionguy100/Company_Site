package com.fasttrack.greenteam.GroupFinal.services.impls;

import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.AnnouncementResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.exceptions.BadRequestException;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotAuthorizedException;
import com.fasttrack.greenteam.GroupFinal.exceptions.NotFoundException;
import com.fasttrack.greenteam.GroupFinal.mappers.AnnouncementMapper;
import com.fasttrack.greenteam.GroupFinal.mappers.CompanyMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.AnnouncementRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.CompanyRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.AnnouncementService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    Announcement findAnnouncement(Long id) {
        Optional<Announcement> announcement = announcementRepository.findAnnouncementById(id);
        if(announcement.isEmpty()) {
            throw new NotFoundException("Announcement not found");
        }
        return announcement.get();
    }

    @Override
    public List<AnnouncementResponseDto> getAnnouncements() {
        return announcementMapper.entitiesToDtos(announcementRepository.findAll());
    }
    @Override
    public AnnouncementResponseDto createAnnouncement(AnnouncementRequestDto announcementRequestDto, HttpSession session) {
        Announcement a = announcementMapper.dtoToEntity(announcementRequestDto);

        if (announcementRequestDto.getCompany() == null) {
            throw new IllegalArgumentException("company id is required");
        }
        Company companyRef = companyRepository.getReferenceById(announcementRequestDto.getCompany());
        a.setCompany(companyRef);

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) throw new NotAuthorizedException("User not authorized");
        User user = userRepository.getReferenceById(userId);
        a.setAuthor(user);

        a.setDate(Timestamp.from(Instant.now()));

        Announcement saved = announcementRepository.saveAndFlush(a);
        return announcementMapper.entityToDto(saved);
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
        if (announcementRequestDto.getContent() != null && !announcementRequestDto.getContent().isBlank()) {
            announcement.setContent(announcementRequestDto.getContent().trim());
            touched = true;
        }
        if (announcementRequestDto.getCompany() != null) {
            throw new BadRequestException("Company cannot be changed via PATCH /announcements/{id}");
        }
        if (!touched) {
            throw new BadRequestException("Nothing to update. Provide title and/or Content.");
        }

        Announcement saved = announcementRepository.saveAndFlush(announcement);
        return announcementMapper.entityToDto(saved);
    }
}
