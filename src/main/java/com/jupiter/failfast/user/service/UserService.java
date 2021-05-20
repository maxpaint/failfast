package com.jupiter.failfast.user.service;

import com.jupiter.failfast.notification.NotificationClient;
import com.jupiter.failfast.user.entity.User;
import com.jupiter.failfast.user.exception.EmptyEmailException;
import com.jupiter.failfast.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final NotificationClient notificationClient;

    public String create(User user) {
        var email = user.getEmail();
        if (Strings.isBlank(email)) {
            throw new EmptyEmailException("user doesn't contain email " + user);
        }
        var id = userRepository.save(user);
        return id;
    }

}
