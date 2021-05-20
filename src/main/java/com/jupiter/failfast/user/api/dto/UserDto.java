package com.jupiter.failfast.user.api.dto;

import com.jupiter.failfast.user.api.validator.UniqueUserEmailConstraint;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class UserDto {

    private String email;
    private String firstName;
    private String secondName;
}
