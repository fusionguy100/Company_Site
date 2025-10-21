package com.fasttrack.greenteam.GroupFinal.services;

import java.util.List;

import com.fasttrack.greenteam.GroupFinal.dtos.CompanyRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.CompanyResponseDto;

public interface CompanyService {

    List<CompanyResponseDto> getCompanies();

    CompanyResponseDto getCompany(Long id);

    CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto);

    CompanyResponseDto updateCompany(CompanyRequestDto companyRequestDto, Long id);

    CompanyResponseDto deleteCompany(Long id);
}
