package com.socialmeli.socialmeli.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmeli.socialmeli.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository{
    ArrayList<User> users;

    public UserRepositoryImpl(){
        users = this.loadUserJson();
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
        return users.stream().filter(user -> user.getUserId().equals(id)).findFirst();
    }

    @Override
    public ArrayList<User> findAll() {
        return null;
    }

    private ArrayList<User> loadUserJson(){
        File file = null;

        try {
            file= ResourceUtils.getFile("classpath:json/user.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        ArrayList<User> userList = null;
        TypeReference<ArrayList<User>> typeRef = new TypeReference<>() {};
        try {
            userList = objectMapper.readValue(file, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
