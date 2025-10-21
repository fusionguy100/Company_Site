package com.fasttrack.greenteam.GroupFinal.repositories;

import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository <Team, Long>  {
    List<Team> findByIdIgnoreCase(Long id);
    boolean existsByIdAndActiveIsTrueAndAdminIsTrue(Long userId);
    User findByCredentialsUsernameAndCredentialsPassword(String username, String password);
    boolean existsByIdAndActiveIsFalse(Long id);
}
