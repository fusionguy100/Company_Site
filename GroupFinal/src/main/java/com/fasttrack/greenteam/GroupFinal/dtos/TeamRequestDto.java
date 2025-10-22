package com.fasttrack.greenteam.GroupFinal.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeamRequestDto {
    private String name;

    private String description;

    private Long company;

    List<Long> userIds;
}
