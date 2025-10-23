package com.fasttrack.greenteam.GroupFinal.controllers;

import java.util.List;

import com.fasttrack.greenteam.GroupFinal.dtos.*;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import com.fasttrack.greenteam.GroupFinal.services.CompanyService;

@RestController
@RequestMapping("/companies")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public List<CompanyResponseDto> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponseDto getCompany(@PathVariable Long id) {
        return companyService.getCompany(id);
    }

    @PostMapping
    public CompanyResponseDto createCompany(@RequestBody CompanyRequestDto companyRequestDto) {
        return companyService.createCompany(companyRequestDto);
    }

    @PatchMapping("/{id}")
    public CompanyResponseDto updateCompany(@RequestBody CompanyRequestDto companyRequestDto, @PathVariable Long id) {
        return companyService.updateCompany(companyRequestDto, id);
    }

    @DeleteMapping("/{id}")
    public CompanyResponseDto deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id);
    }

    @GetMapping("/{id}/users")
public List<UserResponseDto> getUsersByCompany(@PathVariable Long id) {
    return companyService.getUsersByCompany(id);
}

@PostMapping("/{id}/users")
public UserResponseDto addUserToCompany(@PathVariable Long id, @RequestParam Long userId) {
    return companyService.addUserToCompany(id, userId);
}

@DeleteMapping("/{id}/users/{userId}")
public UserResponseDto removeUserFromCompany(@PathVariable Long id, @PathVariable Long userId) {
    return companyService.removeUserFromCompany(id, userId);
}

    @GetMapping("/{id}/announcements")
    public List<AnnouncementResponseDto> listAnnouncements(@PathVariable Long id) { return companyService.listAnnouncementsByDateDesc(id); }

    @GetMapping("/{id}/teams")
    public List<TeamResponseDto> getTeamsByCompany(@PathVariable Long id) {
        return companyService.getTeamsByCompany(id);
    }

}


