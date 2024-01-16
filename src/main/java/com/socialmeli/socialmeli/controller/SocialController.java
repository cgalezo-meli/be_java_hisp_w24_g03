package com.socialmeli.socialmeli.controller;

import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SocialController {

    IUserService userService;

    @GetMapping("/users/{userId}/followed/list")
    public ResponseEntity<UserFollowedDto> getAllFollowed(@PathVariable Integer userId){
        return new ResponseEntity<>(userService.listFollowed(userId), HttpStatus.OK);
    }

}
