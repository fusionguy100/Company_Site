package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.CompanyRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.CompanyResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company dtoToEntity(CompanyRequestDto companyRequestDto);
    CompanyResponseDto entityToDto(Company company);
    List<CompanyResponseDto> entitiesToDtos(List<Company> companies);
}
