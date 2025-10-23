package com.fasttrack.greenteam.GroupFinal.services;

import java.util.List;

import com.fasttrack.greenteam.GroupFinal.dtos.*;

public interface CompanyService {

    List<CompanyResponseDto> getCompanies();

    CompanyResponseDto getCompany(Long id);

    CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto);

    CompanyResponseDto updateCompany(CompanyRequestDto companyRequestDto, Long id);

    CompanyResponseDto deleteCompany(Long id);

    List<UserResponseDto> getUsersByCompany(Long companyId);

    UserResponseDto addUserToCompany(Long companyId, Long userId);

    UserResponseDto removeUserFromCompany(Long companyId, Long userId);

    List<AnnouncementResponseDto> listAnnouncements(Long id);
    List<AnnouncementResponseDto> listAnnouncementsByDateDesc(Long id);
    List<TeamResponseDto> getTeamsByCompany(Long id);


}
