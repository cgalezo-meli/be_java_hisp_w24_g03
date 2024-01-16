package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import com.socialmeli.socialmeli.repositories.UserRepositoryImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements IUserService{
    IUserRepository userRepository;
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
}
