package com.socialmeli.socialmeli.repositories;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.entities.User;

import java.util.ArrayList;
import java.util.List;

public interface IUserRepository extends ICrudRepository<User>{

    List<User> listFollowed(Integer userId);
    User userDetails(Integer userId);
}
