package com.socialmeli.socialmeli.controller;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.services.IUserService;
import com.socialmeli.socialmeli.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.socialmeli.socialmeli.dto.PostDto;
import com.socialmeli.socialmeli.services.PostService;

import java.util.List;

@RestController
public class SocialController {
    private final UserService userService;
    private final PostService postService;

    public SocialController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<UserDto> getTotalFollowers(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.getTotalFollowers(userId), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<ResponseDto> follow(@PathVariable("userId") Integer userId,
                                              @PathVariable("userIdToFollow") Integer userIdToFollow) {
        return ResponseEntity.ok(userService.follow(userId, userIdToFollow));
    }

    @PostMapping("/products/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.save(postDto), HttpStatus.OK);
    }
}
