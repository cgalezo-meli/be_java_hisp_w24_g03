package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.dto.UserFollowerDto;

import java.util.List;

public interface IUserService {
    List<UserDto> getAllUsers();
    UserDto getTotalFollowers(Integer userId);
    UserFollowerDto getFollowers(Integer userId);

}
