package com.fasttrack.greenteam.GroupFinal.dtos;

import com.fasttrack.greenteam.GroupFinal.entities.Company;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class AnnouncementResponseDto {
    private Long id;
    private Timestamp date;
    private String title;
    private String message;
    private CompanyResponseDto company;
    private UserResponseDto author;
}
