package com.fasttrack.greenteam.GroupFinal.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    //many teams belong to one company
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    //Many users belong to many teams (join table user_team)
    @ManyToMany(mappedBy="teams")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();


}