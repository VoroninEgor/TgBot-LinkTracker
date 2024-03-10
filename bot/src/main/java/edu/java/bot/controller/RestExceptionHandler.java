package edu.java.bot.controller;

import edu.java.bot.exception.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorDescription = "Invalid LinkUpdate request body";
        return toResponseEntity(e, HttpStatus.BAD_REQUEST, errorDescription);
    }

    private ResponseEntity<ApiErrorResponse> toResponseEntity(
        Throwable ex, HttpStatus status, String errorDescription
    ) {
        log.warn(errorDescription);
        ApiErrorResponse response
            = ApiErrorResponse.toApiErrorResponse(ex, errorDescription, status.toString());
        return new ResponseEntity<>(response, status);
    }
}
