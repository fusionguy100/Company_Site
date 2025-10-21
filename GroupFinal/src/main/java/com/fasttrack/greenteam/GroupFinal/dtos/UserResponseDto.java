package com.fasttrack.greenteam.GroupFinal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponseDto {
    private Long id;

    private ProfileDto profile;
    private String username;
    private boolean active;
    private boolean admin;
    private String status;
}
