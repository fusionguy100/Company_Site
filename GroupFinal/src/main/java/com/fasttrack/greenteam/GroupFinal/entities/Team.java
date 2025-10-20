package com.fasttrack.greenteam.GroupFinal.entities;
import jakarta.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    //many teams belong to one company
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    //Many users belong to many teams (join table user_team)
    @ManyToMany
    @JoinTable(
            name = "user_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Project> projects = new HashSet<>();
}