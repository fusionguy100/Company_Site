package com.fasttrack.greenteam.GroupFinal.dtos;

import com.fasttrack.greenteam.GroupFinal.entities.Company;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnnouncementRequestDto {
    private String title;
    private String content;
    private Long company;
}
