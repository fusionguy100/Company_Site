package com.fasttrack.greenteam.GroupFinal.services.impls;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.fasttrack.greenteam.GroupFinal.exceptions.NotFoundException;
import com.fasttrack.greenteam.GroupFinal.dtos.CompanyRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.CompanyResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.mappers.CompanyMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.CompanyRepository;
import com.fasttrack.greenteam.GroupFinal.services.CompanyService;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyResponseDto> getCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companyMapper.entitiesToDtos(companies);
    }

    @Override
    public CompanyResponseDto getCompany(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if (optionalCompany.isPresent()) {
            return companyMapper.entityToDto(optionalCompany.get());
        } else {
            throw new NotFoundException("Company not found with id: " + id);
        }
    }

    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto) {
        Company company = companyMapper.dtoToEntity(companyRequestDto);
        Company saved = companyRepository.save(company);
        return companyMapper.entityToDto(saved);
    }

    @Override
    public CompanyResponseDto updateCompany(CompanyRequestDto companyRequestDto, Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();

            if (companyRequestDto.getName() != null) {
                company.setName(companyRequestDto.getName());
            }
            if (companyRequestDto.getIndustry() != null) {
                company.setIndustry(companyRequestDto.getIndustry());
            }
            if (companyRequestDto.getLocation() != null) {
                company.setLocation(companyRequestDto.getLocation());
            }

            Company updated = companyRepository.save(company);
            return companyMapper.entityToDto(updated);
        } else {
            throw new NotFoundException("Company not found with id: " + id);
        }
    }

    @Override
    public CompanyResponseDto deleteCompany(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            companyRepository.delete(company);
            return companyMapper.entityToDto(company);
        } else {
            throw new NotFoundException("Company not found with id: " + id);
        }
    }
}
