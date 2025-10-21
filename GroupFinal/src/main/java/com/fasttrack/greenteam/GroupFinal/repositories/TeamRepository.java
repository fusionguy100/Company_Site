package com.fasttrack.greenteam.GroupFinal.repositories;

import com.fasttrack.greenteam.GroupFinal.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findById(Long id);

    List<Team> findByIdIgnoreCase(Long id);

}
