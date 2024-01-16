package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.PostDto;
import com.socialmeli.socialmeli.dto.ProductDto;
import com.socialmeli.socialmeli.repositories.PostRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService{
    private final PostRepositoryImpl postRepository;

    public PostService(PostRepositoryImpl postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return this.postRepository.findAll().stream().map(
                post -> new PostDto(
                        post.getUserId(),
                        post.getDate(),
                        new ProductDto(
                                post.getProduct().getProductId(),
                                post.getProduct().getProductName(),
                                post.getProduct().getType(),
                                post.getProduct().getBrand(),
                                post.getProduct().getColor(),
                                post.getProduct().getNotes()
                        ),
                        post.getCategory(),
                        post.getPrice()
                )
        ).toList();
    }
}
