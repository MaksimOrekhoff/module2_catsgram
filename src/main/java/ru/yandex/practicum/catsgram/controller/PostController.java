package ru.yandex.practicum.catsgram.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class PostController {

    private List<Post> posts = new ArrayList<>();

    @GetMapping("/home/posts")
    public List<Post> findAll() {
        log.debug("Текущее количество постов: {}", posts.size());
        return posts;
    }



    @PostMapping("/home/post")
    @ResponseBody
    public void create(@RequestBody Post post, HttpServletRequest request) {
        posts.add(post);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
    }
}