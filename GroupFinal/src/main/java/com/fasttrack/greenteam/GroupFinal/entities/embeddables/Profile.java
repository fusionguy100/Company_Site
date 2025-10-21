package com.fasttrack.greenteam.GroupFinal.entities.embeddables;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Profile {

    private String first;
    private String last;
    private String email;
    private String phone;
}
