package edu.java.controller;

import edu.java.exception.LinkAlreadyAddedException;
import edu.java.exception.TgChatAlreadyRegisteredException;
import edu.java.exception.TgChatNotExistException;
import edu.java.exception.ValidationErrorResponse;
import edu.java.exception.Violation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MyRestControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        log.warn("Некорректное тело запроса LinkUpdate");
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
            .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(TgChatAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ResponseBody
    public String tgChatAlreadyRegistered(
        TgChatAlreadyRegisteredException e
    ) {
        log.warn("Попытка зарегестрировать уже зареганный чат");

        return "Chat already registered";
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ResponseBody
    public String linkAlreadyAdded(
        LinkAlreadyAddedException e
    ) {
        log.warn("Попытка добавить уже добавленную ссылку");

        return "Link already added";
    }

    @ExceptionHandler(TgChatNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String tgChatNotExist(
        TgChatNotExistException e
    ) {
        log.warn("Попытка удалить несуществующий чат");

        return "Tg chat does not exist";
    }
}
