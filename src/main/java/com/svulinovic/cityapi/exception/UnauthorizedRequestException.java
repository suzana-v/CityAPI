package com.svulinovic.cityapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedRequestException extends RuntimeException {

    public UnauthorizedRequestException(String message) {
        super(message);
    }

}
