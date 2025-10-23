package com.fasttrack.greenteam.GroupFinal.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fasttrack.greenteam.GroupFinal.dtos.ProfileDto;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "firstName", source = "first")
    @Mapping(target = "lastName", source = "last")
    ProfileDto entityToDto(Profile profile);
    
    List<ProfileDto> entitiesToDtos(List<Profile> profiles);
    
    @Mapping(target = "first", source = "firstName")
    @Mapping(target = "last", source = "lastName")
    Profile dtoToEntity(ProfileDto profileDto);
}
