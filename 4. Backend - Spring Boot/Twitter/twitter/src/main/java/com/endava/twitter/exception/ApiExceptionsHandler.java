package com.endava.twitter.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ApiExceptionsHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        //return super.handleMissingServletRequestParameter(ex, headers, status, request);
        final String PARAM_ERROR_MSG = "Required request parameter [%s] for method parameter type String is not present.";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("userId", (String) null)
                .body(String.format(PARAM_ERROR_MSG, Objects.requireNonNull(ex.getParameterName())));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        //return super.handleMethodArgumentNotValid(ex, headers, HttpStatus.I_AM_A_TEAPOT, request);
        final String FIELD_ERROR_MSG = "Field error on [%s] : rejected value.";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("userId", (String) null)
                .body(String.format(FIELD_ERROR_MSG, Objects.requireNonNull(ex.getFieldError()).getField()));
    }

    //MissingRequestHeaderException
    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.UNAUTHORIZED);
    }

}
