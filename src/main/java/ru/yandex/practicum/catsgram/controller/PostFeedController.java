package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.SerializationHelper;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/feed/friends")
public class PostFeedController {

    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    List<Post> getFriendsFeed(@RequestBody String strangeRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        SerializationHelper serializationHelper;
        try {
            String paramsFromString = objectMapper.readValue(strangeRequest, String.class);
            serializationHelper = objectMapper.readValue(paramsFromString, SerializationHelper.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (!(serializationHelper.getSort().equals("asc") || serializationHelper.getSort().equals("desc"))) {
            throw new IncorrectParameterException("sort");
        }

        if (serializationHelper.getSize() == null || serializationHelper.getSize() <= 0) {
            throw new IncorrectParameterException("size");
        }

        if (serializationHelper.getFriends().isEmpty()) {
            throw new IncorrectParameterException("friendsEmails");
        }

        List<Post> result = new ArrayList<>();
        for (String friend : serializationHelper.getFriends()) {
            result.addAll(postService.findAllByUserEmail(friend, serializationHelper.getSize(), serializationHelper.getSort()));
        }
        return result;

    }


}