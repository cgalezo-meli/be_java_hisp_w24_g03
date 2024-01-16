package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    IUserRepository userRepository;
    @Override
    public UserFollowedDto listFollowed(Integer userId) {

        User user = userRepository.userDetails(userId);
        List<User> followed = userRepository.listFollowed(userId);

        if (user != null && user.getFollowed() != null) {
            return new UserFollowedDto(user.getUserId(), user.getUserName(), convertToUserDtoList(user.getFollowed()));
        } else {
            return new UserFollowedDto(user.getUserId(), user.getUserName(), Collections.emptyList());
        }
    }

    private UserDto convertToUserDto(User user) {
        return new UserDto(user.getUserId(), user.getUserName());
    }

    private List<UserDto> convertToUserDtoList(List<User> users) {
        return users.stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }
}
