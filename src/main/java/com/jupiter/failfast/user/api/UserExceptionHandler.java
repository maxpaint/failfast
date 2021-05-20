package com.jupiter.failfast.user.api;

import com.jupiter.failfast.user.exception.EmptyEmailException;
import com.jupiter.failfast.user.exception.UserExistException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice(basePackages = "com.jupiter.failfast.user.api")
public class UserExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public String handleUserExistException(HttpServletRequest request, Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EmptyEmailException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public String handleUserEmptyEmailException(HttpServletRequest request, Exception ex) {
        return ex.getMessage();
    }














    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public List<ValidationError> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private List<ValidationError> processFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @RequiredArgsConstructor
    @Getter
    static class ValidationError {
        private final String field;
        private final String message;
    }


    /*@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "RuntimeException occured")
    @ExceptionHandler(RuntimeException.class)
    public void handleIOException() {
    }*/
}
