package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.PostDto;
import com.socialmeli.socialmeli.dto.ProductDto;
import com.socialmeli.socialmeli.entities.Post;
import com.socialmeli.socialmeli.exceptions.BadRequestException;
import com.socialmeli.socialmeli.mapper.Mapper;
import com.socialmeli.socialmeli.repositories.PostRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService{
    private final PostRepositoryImpl postRepository;

    private final Mapper mapper;

    public PostService(PostRepositoryImpl postRepository, Mapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
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

    @Override
    public PostDto save(PostDto postDto) {
        if(postDto.user_id() == null || postDto.date() == null || postDto.product() == null || postDto.category() == null
                || postDto.price() == null){
            throw new BadRequestException("Los datos ingresados no son correctos.");
        }

        Post post = mapper.convertDtoToPost(postDto);
        var postList = postRepository.save(post);
        return mapper.convertPostToDto((Post) postList);
    }
}
