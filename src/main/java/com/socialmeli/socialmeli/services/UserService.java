package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.exceptions.BadRequestException;
import com.socialmeli.socialmeli.exceptions.NotFoundException;
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
    public ResponseDto follow(Integer userId, Integer userIdToFollow) {
        User user = this.userExists(userId);
        User userToFollow = this.userExists(userIdToFollow);

        // Verify if user already follows to userToFollow
        if (this.userIsFollowerOf(user, userToFollow))
            throw new BadRequestException("El usuario " + userId + " ya sigue al usuario " + userIdToFollow);

        // Set user as "follower" in userToFollow
        userToFollow.getFollowers().add(user);

        // Set userToFollow as "followed" in user
        user.getFollowed().add(userToFollow);

        return new ResponseDto("Follow exitoso");
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

    // Verify if user is a follower of userToFollow
    private Boolean userIsFollowerOf(User user, User userToFollow) {
        return user.getFollowed().stream().anyMatch(
                followed -> followed.getUserId() == userToFollow.getUserId()
        );
    }

    // Verify if a user exists by id
    private User userExists(Integer id) {
        var user = this.userRepository.findById(id);
        if (user.isEmpty())
            throw new NotFoundException("El usuario " + id + " no existe");

        return user.get();
    }
}
