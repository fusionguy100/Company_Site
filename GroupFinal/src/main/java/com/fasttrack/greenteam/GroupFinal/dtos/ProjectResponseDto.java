package com.fasttrack.greenteam.GroupFinal.dtos;

import com.fasttrack.greenteam.GroupFinal.entities.Team;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    private TeamResponseDto team;
}
