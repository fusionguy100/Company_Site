package com.fasttrack.greenteam.GroupFinal.mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public class UserMapper {


}
