package ru.yandex.practicum.catsgram.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.service.HackCatService;
//import ru.yandex.practicum.catsgram.service.HackCatService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@Slf4j
public class SimpleController {
    private final HackCatService hackCatService;

    public SimpleController(HackCatService hackCatService) {
        this.hackCatService = hackCatService;
    }

    @GetMapping("/do-hack")
    public Optional<String> doHack() {
        return hackCatService.doHackNow()
                .map(password -> "Ура! Пароль подобран: " + password)
                .or(() -> Optional.of("Не удалось подобрать пароль. "
                        + " Проверьте состояние и настройки базы данных."));
    }

    @GetMapping("/home")
    @ResponseBody
    public String homePage(HttpServletRequest request) {
        return "Котограм";
    }


}