package com.fasttrack.greenteam.GroupFinal.services;


import com.fasttrack.greenteam.GroupFinal.dtos.TeamResponseDto;

import java.util.List;

public interface TeamService {
    List<TeamResponseDto> getTeams();
}
