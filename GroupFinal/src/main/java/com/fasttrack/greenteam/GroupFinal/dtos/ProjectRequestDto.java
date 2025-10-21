package com.fasttrack.greenteam.GroupFinal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectRequestDto {
    private String name;
    private String description;
    private Boolean active;
    private Long team;
}
