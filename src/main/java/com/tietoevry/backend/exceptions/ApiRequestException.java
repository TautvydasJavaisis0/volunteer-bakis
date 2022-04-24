package com.tietoevry.backend.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, HttpStatus conflict, ZonedDateTime z) {
    }
}
