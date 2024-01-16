package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.ResponseDTO;
import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.repositories.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{
    private final UserRepositoryImpl userRepository;

    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(
                user -> new UserDto(
                        user.getUserId(),
                        user.getUserName()
                )
        ).toList();
    }
}
