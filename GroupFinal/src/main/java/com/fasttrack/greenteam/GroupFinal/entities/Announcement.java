package com.fasttrack.greenteam.GroupFinal.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(name="announcements")
@Data
public class Announcement {

    @Id
    @GeneratedValue
    private Long id;

    private Timestamp date;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name="company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;
}
