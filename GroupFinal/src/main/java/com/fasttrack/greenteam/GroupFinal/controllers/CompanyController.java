package com.fasttrack.greenteam.GroupFinal.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import com.fasttrack.greenteam.GroupFinal.dtos.CompanyRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.CompanyResponseDto;
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

    @PostMapping
    public CompanyResponseDto createCompany(@RequestBody CompanyRequestDto companyRequestDto) {
        return companyService.createCompany(companyRequestDto);
    }

    @GetMapping("/{id}")
    public CompanyResponseDto getCompany(@PathVariable Long id) {
        return companyService.getCompany(id);
    }

    @PatchMapping("/{id}")
    public CompanyResponseDto updateCompany(@RequestBody CompanyRequestDto companyRequestDto, @PathVariable Long id) {
        return companyService.updateCompany(companyRequestDto, id);
    }

    @DeleteMapping("/{id}")
    public CompanyResponseDto deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id);
    }
}
