package com.jupiter.failfast.user.repository;

import com.jupiter.failfast.user.entity.User;
import com.jupiter.failfast.user.exception.UserExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final Map<String, User> userDb;

    public String save(User user) {
        var email = user.getEmail();
        if (nonNull(getUserByEmail(email))) {
            userDb.put(email, user);
        } else {
            throw new UserExistException(format("user with %s email exists", email));
        }
        return email;
    }

    @Nullable
    public User getUserByEmail(String email) {
        return userDb.get(email);
    }

}
