package com.fasttrack.greenteam.GroupFinal.controllers;

import com.fasttrack.greenteam.GroupFinal.dtos.ProjectRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.ProjectResponseDto;
import com.fasttrack.greenteam.GroupFinal.services.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponseDto> getProjects() { return projectService.getProjects();}

    @PostMapping
    public ProjectResponseDto createProject(@RequestBody ProjectRequestDto projectRequestDto) { return projectService.createProject(projectRequestDto);}

    @GetMapping("/{id}")
    public ProjectResponseDto getProject(@PathVariable Long id) { return projectService.getProject(id);}

    @PatchMapping("/{id}")
    public ProjectResponseDto updateProject(@RequestBody ProjectRequestDto projectRequestDto, @PathVariable Long id) { return projectService.updateProject(projectRequestDto, id);}

    @DeleteMapping("/{id}")
    public ProjectResponseDto inactivateProject(@PathVariable Long id) { return projectService.inactivateProject(id);}


}
