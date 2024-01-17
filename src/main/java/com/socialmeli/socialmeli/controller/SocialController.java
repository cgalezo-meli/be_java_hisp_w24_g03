package com.socialmeli.socialmeli.controller;

import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.dto.UserFollowedPostsDto;
import com.socialmeli.socialmeli.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.services.UserService;

import org.springframework.web.bind.annotation.*;

import com.socialmeli.socialmeli.dto.PostDto;
import com.socialmeli.socialmeli.services.PostService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    @GetMapping("/users/{userId}/followed/list")
    public ResponseEntity<UserFollowedDto> getAllFollowed(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.listFollowed(userId), HttpStatus.OK);
    }

    @PostMapping("/products/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.save(postDto), HttpStatus.OK);
    }

    @GetMapping("/products/followed/{userId}/list")
    public ResponseEntity<UserFollowedPostsDto> getLastTwoWeeksFollowedPosts(@PathVariable Integer userId){
        List<UserDto> followedList = userService.listFollowed(userId).followed();
        List<PostDto> allFollowedPosts = new ArrayList<>();

        for (UserDto userDto : followedList){
            allFollowedPosts.addAll(postService.getUserPosts(userDto.user_id()));
        }

        LocalDate currentDate = LocalDate.now();

        List<PostDto> lastTwoWeeksFollowedPosts = postService.sortDateDesc(allFollowedPosts).stream().filter(post -> ChronoUnit.DAYS.between(post.date(),currentDate)<=14).toList();
        return new ResponseEntity<>(new UserFollowedPostsDto(userId, lastTwoWeeksFollowedPosts), HttpStatus.OK);
    }
}

