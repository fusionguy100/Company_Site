package com.fasttrack.greenteam.GroupFinal.services;

import com.fasttrack.greenteam.GroupFinal.dtos.UserRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.User;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);

    UserResponseDto deleteUser(Long id);

    List<UserResponseDto> getActiveUsers();

    List<UserResponseDto> getAdminUsers();

    boolean isAdmin(Long userId);

    User validateCredentials(String username, String password);
//    List<CompaniesDto> getUserCompanies(Long id);
//
//    List<TeamsDto> getUserTeams(Long id);

}
