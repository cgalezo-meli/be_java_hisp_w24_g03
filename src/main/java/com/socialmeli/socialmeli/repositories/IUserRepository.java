package com.socialmeli.socialmeli.repositories;

import com.socialmeli.socialmeli.entities.User;

import java.util.ArrayList;

public interface IUserRepository extends ICrudRepository<User>{
    ArrayList<User> loadUserJson();
}
