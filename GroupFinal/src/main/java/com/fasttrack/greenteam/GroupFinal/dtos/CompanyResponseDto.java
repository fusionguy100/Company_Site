package com.fasttrack.greenteam.GroupFinal.dtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String description;
    private String website;
    private String industry;
    private String location;
    private String contactEmail;
    private List<TeamSummaryDto> teams;
    private List<AnnouncementSummaryDto> announcements;
    private List<UserSummaryDto> users;

}
