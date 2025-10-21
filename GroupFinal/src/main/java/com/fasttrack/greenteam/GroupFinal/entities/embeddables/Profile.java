package com.fasttrack.greenteam.GroupFinal.entities.embeddables;

import jakarta.persistence.Embeddable;

@Embeddable
public class Profile {

    private String first;
    private String last;
    private String email;
    private String phone;
}
