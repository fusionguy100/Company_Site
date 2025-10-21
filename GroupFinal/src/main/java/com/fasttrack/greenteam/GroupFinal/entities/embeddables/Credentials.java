package com.fasttrack.greenteam.GroupFinal.entities.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Credentials {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
}
