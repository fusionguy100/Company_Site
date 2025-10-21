package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.TeamRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.TeamResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Team;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TeamMapper {


    @Mapping(source = "company.id", target = "company")
    TeamResponseDto toDto(Team team);


    @Mapping(target = "company", source = "company", qualifiedByName = "mapCompanyIdToCompany")
    Team toEntity(TeamRequestDto dto);

    // Helper method: convert companyId (Long) to a Company object reference
    @Named("mapCompanyIdToCompany")
    default Company mapCompanyIdToCompany(Long companyId) {
        if (companyId == null) return null;
        Company company = new Company();
        company.setId(companyId);
        return company;
    }
}
