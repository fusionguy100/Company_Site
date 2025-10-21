package com.fasttrack.greenteam.GroupFinal.repositories;

import com.fasttrack.greenteam.GroupFinal.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findById(Long id);
}
