package com.socialmeli.socialmeli.repositories;

import com.socialmeli.socialmeli.entities.User;

import java.util.ArrayList;
import java.util.Optional;

public class UserRepositoryImpl implements IUserRepository{
    ArrayList<User> users;

    public UserRepositoryImpl() {
        this.users = this.loadUserJson();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public ArrayList<User> findAll() {
        return null;
    }

    @Override
    public ArrayList<User> loadUserJson() {
        return null;
    }
}
