package com.example.sns_project.domain.post.exception;

import com.example.sns_project.global.exception.CustomApiException;
import org.springframework.http.HttpStatus;

public class PostNotFound extends CustomApiException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}