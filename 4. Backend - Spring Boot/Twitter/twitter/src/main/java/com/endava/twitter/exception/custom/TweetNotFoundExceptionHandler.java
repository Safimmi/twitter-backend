package com.endava.twitter.exception.custom;

import com.endava.twitter.exception.ExceptionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class TweetNotFoundExceptionHandler {

    @ExceptionHandler(value = {TweetNotFoundException.class})
    public ResponseEntity<Object> handleTweetNotFoundException (TweetNotFoundException e){

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
                .header("tweetId", (String) null)
                .body(exceptionTemplate);

    }

}
