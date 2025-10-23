package com.fasttrack.greenteam.GroupFinal.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasttrack.greenteam.GroupFinal.dtos.*;
import com.fasttrack.greenteam.GroupFinal.mappers.AnnouncementMapper;
import com.fasttrack.greenteam.GroupFinal.mappers.TeamMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.AnnouncementRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.fasttrack.greenteam.GroupFinal.exceptions.NotFoundException;
import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import com.fasttrack.greenteam.GroupFinal.mappers.CompanyMapper;
import com.fasttrack.greenteam.GroupFinal.mappers.UserMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.CompanyRepository;
import com.fasttrack.greenteam.GroupFinal.repositories.UserRepository;
import com.fasttrack.greenteam.GroupFinal.services.CompanyService;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

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
            if (companyRequestDto.getDescription() != null) {
                company.setDescription(companyRequestDto.getDescription());
            }
            if (companyRequestDto.getWebsite() != null) {
                company.setWebsite(companyRequestDto.getWebsite());
            }
            if (companyRequestDto.getContactEmail() != null) {
                company.setContactEmail(companyRequestDto.getContactEmail());
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

    @Override
    public List<UserResponseDto> getUsersByCompany(Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);

        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            List<User> users = company.getUsers();
            List<UserResponseDto> result = new ArrayList<>();

            for (User user : users) {
                result.add(userMapper.entityToDto(user));
            }

            return result;
        } else {
            throw new NotFoundException("Company not found with id: " + companyId);
        }
    }

    @Override
    public UserResponseDto addUserToCompany(Long companyId, Long userId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalCompany.isEmpty()) {
            throw new NotFoundException("Company not found with id: " + companyId);
        }

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found with id: " + userId);
        }

        Company company = optionalCompany.get();
        User user = optionalUser.get();

        if (!company.getUsers().contains(user)) {
            company.getUsers().add(user);
            companyRepository.save(company);
        }

    if (!user.getCompanies().contains(company)) {
        user.getCompanies().add(company);
        userRepository.save(user);
    }
        return userMapper.entityToDto(user);
    }

@Override
public UserResponseDto removeUserFromCompany(Long companyId, Long userId) {
    Optional<Company> optionalCompany = companyRepository.findById(companyId);
    Optional<User> optionalUser = userRepository.findById(userId);

    if (optionalCompany.isEmpty()) {
        throw new NotFoundException("Company not found with id: " + companyId);
    }

    if (optionalUser.isEmpty()) {
        throw new NotFoundException("User not found with id: " + userId);
    }

    Company company = optionalCompany.get();
    User user = optionalUser.get();

    if (!company.getUsers().contains(user)) {
        throw new NotFoundException("User with id " + userId + " is not associated with company id " + companyId);
    }

    company.getUsers().remove(user);

    user.getCompanies().remove(company);

    companyRepository.save(company);
    userRepository.save(user);

    return userMapper.entityToDto(user);
}

    @Override
    public List<AnnouncementResponseDto> listAnnouncements(Long id) {
        return announcementMapper.entitiesToDtos(announcementRepository.findByCompanyId(id));
    }

    public List<TeamResponseDto> getTeamsByCompany(Long companyId) {
        return teamRepository.findByCompanyId(companyId)
                .stream()
                .map(teamMapper::entityToDto)
                .toList();
    }

    public List<AnnouncementResponseDto> listAnnouncementsByDateDesc(Long id) {
        return announcementMapper.entitiesToDtos(announcementRepository.findTop10ByCompanyIdOrderByDateDesc(id));
    }


}
