package com.fasttrack.greenteam.GroupFinal.mappers;

import com.fasttrack.greenteam.GroupFinal.dtos.CredentialsDto;
import com.fasttrack.greenteam.GroupFinal.entities.embeddables.Credentials;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    Credentials dtoToEntity(CredentialsDto credentialsDto);
    List<CredentialsDto> entitiesToDtos(List<Credentials> credentials);
    CredentialsDto entityToDto(Credentials credentials);
}
