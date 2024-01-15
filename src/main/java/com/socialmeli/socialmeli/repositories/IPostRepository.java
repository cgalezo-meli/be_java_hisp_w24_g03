package com.socialmeli.socialmeli.repositories;

import com.socialmeli.socialmeli.entities.Post;

import java.util.ArrayList;

public interface IPostRepository extends ICrudRepository{
    ArrayList<Post> loadPostJson();
}
