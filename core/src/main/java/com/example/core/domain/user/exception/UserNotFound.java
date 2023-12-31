package com.example.core.domain.user.exception;

import com.example.core.config.exception.CustomApiException;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserNotFound extends CustomApiException {

    private static final String MESSAGE = "존재하지 않는 이메일 입니다.";

    public UserNotFound() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return NOT_FOUND;
    }
}
