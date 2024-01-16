package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.dto.UserDto;
import java.util.List;

public interface IUserService {
    List<UserDto> getAllUsers();
    UserDto getTotalFollowers(Integer userId);
    ResponseDto follow(Integer userId, Integer userIdToFollow);
}
