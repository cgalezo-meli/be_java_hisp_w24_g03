package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.exceptions.BadRequestException;
import com.socialmeli.socialmeli.mapper.Mapper;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import com.socialmeli.socialmeli.repositories.UserRepositoryImpl;
import com.socialmeli.socialmeli.dto.UserFollowerDto;
import org.springframework.stereotype.Service;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{
    IUserRepository userRepository;
    Mapper mapper = new Mapper();

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getTotalFollowers(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (Objects.isNull(user))
            return null;
        //devolver error

        return new UserDto(userId, user.getUserName(), user.getFollowers().size());
    }

    @Override
    public UserFollowerDto getFollowers(Integer userId, String order) {
        User user = userRepository.findById(userId).orElse(null);
        System.out.println(user.getUserId()+"HOLAAA");
        if (Objects.isNull(user)){
            throw new BadRequestException("Usuario no existe.");
        }
        List<UserDto> followerList = sortFollower(user.getFollowers(),order);

        return new UserFollowerDto(user.getUserId(), user.getUserName(), followerList);
    }

    private List<UserDto> sortFollower(List<User> usersFollower, String order){
        switch(order){
            case "name_asc":
                return mapper.convertToUserDtoList(usersFollower.stream()
                        .sorted(Comparator.comparing(User::getUserName))
                        .collect(Collectors.toList()));
            case "name_desc":
                return mapper.convertToUserDtoList(usersFollower.stream()
                        .sorted(Comparator.comparing(User::getUserName).reversed())
                        .collect(Collectors.toList()));
            default:
                return mapper.convertToUserDtoList(usersFollower);
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(
                user -> new UserDto(
                        user.getUserId(),
                        user.getUserName(),
                        user.getFollowers().size())
        ).toList();
    }

}

