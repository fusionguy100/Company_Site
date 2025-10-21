package com.fasttrack.greenteam.GroupFinal.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.fasttrack.greenteam.GroupFinal.dtos.CompanyRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.CompanyResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    Company dtoToEntity(CompanyRequestDto companyRequestDto);
    CompanyResponseDto entityToDto(Company company);
    List<CompanyResponseDto> entitiesToDtos(List<Company> companies);
}
