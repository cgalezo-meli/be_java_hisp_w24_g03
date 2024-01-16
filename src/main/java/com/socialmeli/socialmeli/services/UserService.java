package com.socialmeli.socialmeli.services;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.exceptions.BadRequestException;
import com.socialmeli.socialmeli.exceptions.NotFoundException;
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
            throw new BadRequestException("No se encontro un usuario con el id " + userId);

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

    public ResponseDto unfollow(Integer userId, Integer userIdToFollow) {
        User user = this.userExists(userId);
        User userToUnfollow = this.userExists(userIdToFollow);

        // Verify if user isn't a follower of userToUnfollow
        if (!this.userIsFollowerOf(user, userToUnfollow))
            throw new BadRequestException("El usuario " + userId + " no sigue al usuario " + userIdToFollow);

        // Remove user as "follower" in userToUnfollow
        userToUnfollow.getFollowers().remove(user);

        // Remove userToUnfollow as "followed" in user
        user.getFollowed().remove(userToUnfollow);
        return new ResponseDto("Unfollow exitoso");
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
                followed -> followed.getUserId().equals(userToFollow.getUserId())
        );
    }

    // Verify if a user exists by id
    private User userExists(Integer id) {
        var user = this.userRepository.findById(id);
        if (user.isEmpty())
            throw new NotFoundException("El usuario " + id + " no existe");

        return user.get();
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
