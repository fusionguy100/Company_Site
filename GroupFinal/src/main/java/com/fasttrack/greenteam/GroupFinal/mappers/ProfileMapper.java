package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.ProfileDto;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto entityToDto(Profile profile);
    List<ProfileDto> entitiesToDtos(List<Profile> profiles);
    Profile dtoToEntity(ProfileDto profileDto);
}
