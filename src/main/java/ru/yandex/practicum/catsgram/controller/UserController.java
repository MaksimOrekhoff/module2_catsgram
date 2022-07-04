package ru.yandex.practicum.catsgram.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import javax.servlet.http.HttpServletRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<String, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @PostMapping
    @ResponseBody
    public User create(@RequestBody User user, HttpServletRequest request) {
        if(user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
        if(users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }
        users.put(user.getEmail(), user);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return user;
    }

    @PutMapping
    public User put(@RequestBody User user) {
        if(user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
        users.put(user.getEmail(), user);
        log.debug("Текущее количество пользователей: {}", users.size());
        return user;
    }
}