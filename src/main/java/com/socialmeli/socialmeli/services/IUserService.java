package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.dto.UserDto;
import java.util.List;

import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.dto.UserFollowersDto;
import com.socialmeli.socialmeli.entities.User;

import java.util.ArrayList;

public interface IUserService {
    List<UserDto> getAllUsers();
    UserFollowersDto getTotalFollowers(Integer userId);
    ResponseDto follow(Integer userId, Integer userIdToFollow);
    ResponseDto unfollow(Integer userId, Integer userIdToFollow);
    UserFollowedDto listFollowed(Integer userId, String order);
}
