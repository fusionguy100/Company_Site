package com.fasttrack.greenteam.GroupFinal.services.impls;


import com.fasttrack.greenteam.GroupFinal.dtos.TeamResponseDto;
import com.fasttrack.greenteam.GroupFinal.mappers.TeamMapper;
import com.fasttrack.greenteam.GroupFinal.repositories.TeamRepository;
import com.fasttrack.greenteam.GroupFinal.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    TeamRepository teamRepository;
    TeamMapper teamMapper;

    @Override
    public List<TeamResponseDto> getTeams() {
        return
    }
}
