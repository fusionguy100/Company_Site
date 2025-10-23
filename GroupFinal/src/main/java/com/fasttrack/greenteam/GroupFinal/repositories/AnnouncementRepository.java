package com.fasttrack.greenteam.GroupFinal.repositories;

import com.fasttrack.greenteam.GroupFinal.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    Optional<Announcement> findAnnouncementById(Long id);
    List<Announcement> findByCompanyId(Long companyId);

    List<Announcement> findByCompanyIdOrderByDateDesc(Long id);

    List<Announcement> findTop10ByCompanyIdOrderByDateDesc(Long id);
}
