package com.fasttrack.greenteam.GroupFinal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSummaryDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean admin;
    private Boolean active;
}
