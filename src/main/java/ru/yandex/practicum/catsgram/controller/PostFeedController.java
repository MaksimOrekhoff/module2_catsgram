package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController()
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

        if (serializationHelper != null) {
            List<Post> result = new ArrayList<>();
            for (String friend : serializationHelper.friendsEmail) {
                result.addAll(postService.findAllByUserEmail(friend, serializationHelper.size, serializationHelper.sort));
            }
            return result;
        } else {
            throw new RuntimeException("Не удалось обработать запрос.");
        }
    }


    static class SerializationHelper {
        private String sort;
        private Integer size;
        private List<String> friendsEmail;

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public List<String> getFriends() {
            return friendsEmail;
        }

        public void setFriends(List<String> friends) {
            this.friendsEmail = friends;
        }
    }
}