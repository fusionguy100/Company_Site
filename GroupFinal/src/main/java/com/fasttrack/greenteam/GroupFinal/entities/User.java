package com.fasttrack.greenteam.GroupFinal.entities;

import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    Profile profile;
    @Embedded
    Credentials credentials;

    private Boolean admin;
    private Boolean active;
    private String status;

    @OneToMany(mappedBy="author")
    private List<Announcement> announcements = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="user_company",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="company_id")
    )
    private List<Company> companies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="user_team",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="team_id")
    )
    private List<Team> teams = new ArrayList<>();

}
