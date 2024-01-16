package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.UserDto;
import java.util.List;

import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.entities.User;

import java.util.ArrayList;

public interface IUserService {
    List<UserDto> getAllUsers();
    UserDto getTotalFollowers(Integer userId);


    UserFollowedDto listFollowed(Integer userId);
}
