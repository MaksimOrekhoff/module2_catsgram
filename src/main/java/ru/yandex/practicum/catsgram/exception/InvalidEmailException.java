package ru.yandex.practicum.catsgram.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String err) {
        super(err);
    }
}