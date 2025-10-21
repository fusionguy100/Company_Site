package com.fasttrack.greenteam.GroupFinal.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue
    private Long id;
}
