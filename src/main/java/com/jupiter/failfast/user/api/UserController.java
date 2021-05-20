package com.jupiter.failfast.user.api;

import com.jupiter.failfast.user.api.dto.UserDto;
import com.jupiter.failfast.user.entity.User;
import com.jupiter.failfast.user.mapper.UserMapper;
import com.jupiter.failfast.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String create(@RequestBody UserDto userDto) {
        var user = userMapper.map(userDto);
        return userService.create(user);
    }
}
