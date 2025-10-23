package com.fasttrack.greenteam.GroupFinal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementSummaryDto {
    private Long id;
    private Timestamp date;
    private String title;
    private String content;
    private String authorUsername;
}