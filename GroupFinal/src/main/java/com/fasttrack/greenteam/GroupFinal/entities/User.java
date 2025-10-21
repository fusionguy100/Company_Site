package com.fasttrack.greenteam.GroupFinal.entities;

import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    Profile profile;
    @Embedded
    Credentials credentials;

    private boolean active;
    private boolean admin;
    private String status;

    @ManyToMany
    private List<Company> announcements;
}
