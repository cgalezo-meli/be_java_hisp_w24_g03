package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.mapper.Mapper;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import com.socialmeli.socialmeli.repositories.UserRepositoryImpl;
import com.socialmeli.socialmeli.dto.UserFollowedDto;
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
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(
                user -> new UserDto(
                        user.getUserId(),
                        user.getUserName(),
                        user.getFollowers().size())
        ).toList();
    }

    @Override
    public UserFollowedDto listFollowed(Integer userId, String order) {

        User user = userRepository.findById(userId).orElse(null);
        List<User> followed = userRepository.listFollowed(userId);
        List<UserDto> followedList = sortFollowed(followed,order);

        return new UserFollowedDto(user.getUserId(), user.getUserName(), followedList);
    }

    private List<UserDto> sortFollowed(List<User> usersFollowed, String order){
        switch(order){
            case "name_asc":
                return mapper.convertToUserDtoList(usersFollowed.stream()
                        .sorted(Comparator.comparing(User::getUserName))
                        .collect(Collectors.toList()));
            case "name_desc":
                return mapper.convertToUserDtoList(usersFollowed.stream()
                        .sorted(Comparator.comparing(User::getUserName).reversed())
                        .collect(Collectors.toList()));
            default:
                return null;
        }
    }
    
}
