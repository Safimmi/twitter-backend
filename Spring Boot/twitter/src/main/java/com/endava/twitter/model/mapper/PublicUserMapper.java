package com.endava.twitter.model.mapper;

import com.endava.twitter.model.dto.UserDto;
import com.endava.twitter.model.entity.PublicUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublicUserMapper {
    PublicUserMapper PUBLIC_USER_INSTANCE = Mappers.getMapper(PublicUserMapper.class);
    PublicUser toEntity(UserDto userDto);
}
