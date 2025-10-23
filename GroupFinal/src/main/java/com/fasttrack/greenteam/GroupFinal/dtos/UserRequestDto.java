package com.fasttrack.greenteam.GroupFinal.dtos;

import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
    CredentialsDto credentials;
    ProfileDto profile;
    Boolean isAdmin;
    Long companyId;
}
