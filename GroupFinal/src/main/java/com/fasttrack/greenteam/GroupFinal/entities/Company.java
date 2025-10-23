package com.fasttrack.greenteam.GroupFinal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String website;

    private String industry;

    private String location;

    private String contactEmail;

    @OneToMany(mappedBy = "company")
    private List<Announcement> announcements = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    private List<Team> teams = new ArrayList<>();

    @ManyToMany(mappedBy = "companies")
    private List<User> users = new ArrayList<>();


}
