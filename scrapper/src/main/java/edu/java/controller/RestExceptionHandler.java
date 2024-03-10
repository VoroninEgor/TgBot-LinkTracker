package edu.java.controller;

import edu.java.exception.ApiErrorResponse;
import edu.java.exception.LinkAlreadyAddedException;
import edu.java.exception.TgChatAlreadyRegisteredException;
import edu.java.exception.TgChatNotExistException;
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

    @ExceptionHandler(TgChatAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> tgChatAlreadyRegistered(TgChatAlreadyRegisteredException e) {
        String errorDescription = "Chat has already been registered";
        return toResponseEntity(e, HttpStatus.CONFLICT, errorDescription);
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    public ResponseEntity<ApiErrorResponse> linkAlreadyAdded(LinkAlreadyAddedException e) {
        String errorDescription = "Link has already been tracked";
        return toResponseEntity(e, HttpStatus.CONFLICT, errorDescription);
    }

    @ExceptionHandler(TgChatNotExistException.class)
    public ResponseEntity<ApiErrorResponse> tgChatNotExist(TgChatNotExistException e) {
        String errorDescription = "TgChat does not exist";
        return toResponseEntity(e, HttpStatus.NOT_FOUND, errorDescription);
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
