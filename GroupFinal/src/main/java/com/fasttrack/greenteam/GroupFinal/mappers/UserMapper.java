package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.UserRequestDto;
import com.fasttrack.greenteam.GroupFinal.dtos.UserResponseDto;
import com.fasttrack.greenteam.GroupFinal.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class,
        CompanyMapper.class, TeamMapper.class})
public interface UserMapper {
    @Mapping(target = "username", source = "credentials.username")
    UserResponseDto entityToDto(User user);
    List<UserResponseDto> entitiesToDtos(List<User> users);
    User dtoToEntity(UserRequestDto userRequestDto);

}
