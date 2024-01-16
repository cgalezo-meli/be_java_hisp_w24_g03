package com.socialmeli.socialmeli.repositories;

import com.socialmeli.socialmeli.entities.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
@Repository
public class PostRepositoryImpl implements IPostRepository{
    ArrayList<Post> posts;

    public PostRepositoryImpl() {
        this.posts = this.loadPostJson();
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public Optional<Post> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public ArrayList<Post> findAll() {
        return null;
    }

    @Override
    public ArrayList<Post> loadPostJson() {
        return null;
    }
}
