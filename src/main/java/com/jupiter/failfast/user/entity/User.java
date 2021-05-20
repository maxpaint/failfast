package com.jupiter.failfast.user.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Data
@Accessors(chain = true)
public class User {
    private final String email;
    private final String firstName;
    private final String secondName;
}
