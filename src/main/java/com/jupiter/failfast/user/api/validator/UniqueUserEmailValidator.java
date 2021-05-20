package com.jupiter.failfast.user.api.validator;

import com.jupiter.failfast.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmailConstraint, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(UniqueUserEmailConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return isNull(userRepository.getUserByEmail(email));
    }
}
