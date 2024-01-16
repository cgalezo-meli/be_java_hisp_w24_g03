package com.socialmeli.socialmeli.controller;

import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.services.UserService;
import com.socialmeli.socialmeli.dto.PostDto;
import com.socialmeli.socialmeli.services.PostService;
import org.springframework.web.bind.annotation.RestController;

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
}

@GetMapping("/users/{userId}/followed/list")
public ResponseEntity<UserFollowedDto> getAllFollowed(@PathVariable Integer userId){
    return new ResponseEntity<>(userService.listFollowed(userId), HttpStatus.OK);
}
