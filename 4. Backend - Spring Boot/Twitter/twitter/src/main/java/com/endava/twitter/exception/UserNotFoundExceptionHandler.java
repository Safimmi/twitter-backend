package com.endava.twitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class UserNotFoundExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException (UserNotFoundException e){

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ExceptionTemplate exceptionTemplate = new ExceptionTemplate(
                e.getMessage(),
                e.getCause(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        e.printStackTrace();
        //return new ResponseEntity<>(exceptionTemplate, httpStatus);
        return ResponseEntity
                .status(httpStatus)
                .header("userId", (String) null)
                .body(exceptionTemplate);

    }

}
