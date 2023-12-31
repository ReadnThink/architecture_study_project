package com.example.core.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomApiException extends RuntimeException{

    public CustomApiException(String message){
        super(message);
    }

    public abstract HttpStatus getStatus();

}
