package com.fasttrack.greenteam.GroupFinal.repositories;

import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Optional<Announcement> findAnnouncementById(Long id);
}
