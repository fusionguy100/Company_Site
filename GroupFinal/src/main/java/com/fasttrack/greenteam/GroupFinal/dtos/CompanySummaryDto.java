package com.fasttrack.greenteam.GroupFinal.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySummaryDto {
    private Long id;
    private String name;
    private String description;
}
