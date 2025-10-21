package com.fasttrack.greenteam.GroupFinal.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private boolean active;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;
}
