package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.PostDto;

import java.util.List;

public interface IPostService {
    List<PostDto> getAllPosts();
    PostDto save(PostDto postDto);
    List<PostDto> getUserPosts(Integer userId);
    List<PostDto> sortDateAsc(List<PostDto> postsDtoList);
    List<PostDto> sortDateDesc(List<PostDto> postsDtoList);
}
