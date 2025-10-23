package com.fasttrack.greenteam.GroupFinal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class TeamResponseDto {

    private Long id;

    private String name;

    private String description;

    private CompanySummaryDto company;

    private List<ProjectSummaryDto> projects;

    private List<UserSummaryDto> users;
}
