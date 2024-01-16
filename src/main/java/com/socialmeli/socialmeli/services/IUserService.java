package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.UserDto;

public interface IUserService {
    UserDto getTotalFollowers(int userId);
}
