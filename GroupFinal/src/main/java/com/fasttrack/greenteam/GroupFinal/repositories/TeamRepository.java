package com.fasttrack.greenteam.GroupFinal.repositories;

import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository <Team, Long>  {
//    List<Team> findByIdIgnoreCase(Long id);
//    boolean existsByIdAndActiveIsTrueAndAdminIsTrue(Long userId);
//    User findByCredentialsUsernameAndCredentialsPassword(String username, String password);
//    boolean existsByIdAndActiveIsFalse(Long id);
      List<Team> findByCompanyId(Long companyId);
}
