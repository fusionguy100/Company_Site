package com.fasttrack.greenteam.GroupFinal.repositories;

import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByCredentialsUsernameAndActiveIsFalse(String username);

    boolean existsByCredentialsUsernameAndActiveIsTrue(String username);

    boolean existsByIdAndActiveIsTrueAndAdminIsTrue(Long userId);

    User findByCredentialsUsernameAndCredentialsPassword(String username, String password);

    boolean existsByIdAndActiveIsFalse(Long id);

    List<User> findAllByActiveIsTrue();

    List<User> findAllByAdminIsTrue();

    Optional<User> findById(Long id);

    boolean existsByCredentialsUsername(String username);
}
