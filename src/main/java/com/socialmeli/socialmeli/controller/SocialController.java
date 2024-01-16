package com.socialmeli.socialmeli.controller;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import com.socialmeli.socialmeli.services.IUserService;
import com.socialmeli.socialmeli.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialController {
    IUserService userService;

    public SocialController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/followers/count")
    public ResponseEntity<UserDto> getTotalFollowers(@PathVariable Integer userId){
        return new ResponseEntity<>(userService.getTotalFollowers(userId), HttpStatus.OK);
    }
}
