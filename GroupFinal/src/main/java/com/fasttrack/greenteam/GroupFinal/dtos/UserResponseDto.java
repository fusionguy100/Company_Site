package com.fasttrack.greenteam.GroupFinal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class UserResponseDto {
    private Long id;

    private ProfileDto profile;
    private String username;
    private boolean active;
    private boolean admin;
    private String status;
    private List<TeamSummaryDto> teams;
    private List<CompanySummaryDto> companies;
    private List<AnnouncementSummaryDto> announcements;
}
