package com.jupiter.failfast.user.mapper;

import com.jupiter.failfast.user.api.dto.UserDto;
import com.jupiter.failfast.user.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    User map(UserDto userDto);
}
