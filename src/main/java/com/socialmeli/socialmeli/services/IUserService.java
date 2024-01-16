package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.entities.User;

import java.util.ArrayList;

public interface IUserService {

    UserFollowedDto listFollowed(Integer userId);
}
