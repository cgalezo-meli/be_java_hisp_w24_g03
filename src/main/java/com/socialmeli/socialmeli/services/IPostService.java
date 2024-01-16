package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.PostDto;

import java.util.List;

public interface IPostService {
    List<PostDto> getAllPosts();
}
