package com.fasttrack.greenteam.GroupFinal.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDto {

    private String name;
    private String description;
    private String website;
    private String industry;
    private String location;
    private String contactEmail;
}
