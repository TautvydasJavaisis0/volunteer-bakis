package com.tietoevry.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.tietoevry.backend.exceptions.ExceptionMessages;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler{
    @ExceptionHandler(value = {ApiRequestException.class} )
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){

        ApiException apiException = new ApiException(
            ExceptionMessages.ApiRequestExceptionMsg,
            HttpStatus.BAD_REQUEST,
            ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class} )
    public ResponseEntity<Object> handleApiRequestException(DataIntegrityViolationException e){

        ApiException apiException = new ApiException(
            e.getMessage(),
            HttpStatus.BAD_REQUEST,
            ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoSuchElementException.class} )
    public ResponseEntity<Object> handleApiRequestException(NoSuchElementException e){

        ApiException apiException = new ApiException(
            ExceptionMessages.NoSuchElementExceptionMsg,
            HttpStatus.BAD_REQUEST,
            ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class} )
    public ResponseEntity<Object> handleApiRequestException(MethodArgumentNotValidException e){

        ApiException apiException = new ApiException(
            ExceptionMessages.MethodArgumentNotValidExceptionMsg,
            HttpStatus.BAD_REQUEST,
            ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class} )
    public ResponseEntity<Object> handleApiRequestException(ConstraintViolationException e) {

        ApiException apiException = new ApiException(
            ExceptionMessages.ConstraintViolationExceptionMsg,
            HttpStatus.BAD_REQUEST,
            ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class} )
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException e) {

        ApiException apiException = new ApiException(
            ExceptionMessages.IllegalArgumentExceptionMsg,
            HttpStatus.BAD_REQUEST,
            ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
