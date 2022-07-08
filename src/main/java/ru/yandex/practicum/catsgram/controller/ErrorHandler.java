package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.catsgram.exception.*;
import ru.yandex.practicum.catsgram.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse postNotFoundException(final PostNotFoundException postNotFoundException) {
        return new ErrorResponse(postNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userNotFoundException(final UserNotFoundException userNotFoundException) {
        return new ErrorResponse(userNotFoundException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse userNotFoundException(final UserAlreadyExistException userAlreadyExistException) {
        return new ErrorResponse(userAlreadyExistException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidEmailException(final InvalidEmailException invalidEmailException) {
        return new ErrorResponse(invalidEmailException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse incorrectParameterException(final IncorrectParameterException incorrectParameterException) {
        return new ErrorResponse("Ошибка с полем " + incorrectParameterException.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse incorrectParameterException(final Throwable throwable) {
        return new ErrorResponse("Произошла непредвиденная ошибка.");
    }

}
