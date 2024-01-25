package com.socialmeli.socialmeli.unit.service;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.dto.UserFollowersDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.exceptions.BadRequestException;
import com.socialmeli.socialmeli.exceptions.NotFoundException;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import com.socialmeli.socialmeli.repositories.UserRepositoryImpl;
import com.socialmeli.socialmeli.services.IUserService;
import com.socialmeli.socialmeli.services.UserService;
import com.socialmeli.socialmeli.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    Utils utils = new Utils();

    @Test
    @DisplayName("Test to follow an existing user")
    void followToExistentUserTest() {
        // Arrange
        int existentUserId = 1115;
        int followed = 1465;
        ResponseDto expected = new ResponseDto("Follow exitoso");

        // when-then
        Mockito.when(userRepository.findById(followed)).thenReturn(Optional.of(utils.getUSER_1465()));
        Mockito.when(userRepository.findById(existentUserId)).thenReturn(Optional.of(utils.getUSER_1115()));

        // Act
        ResponseDto result =userService.follow(followed, existentUserId);

        // Assert
        Assertions.assertEquals(expected, result, "It was not possible to follow the user");
    }

    @Test
    @DisplayName("Test to follow a non-existent user")
    void followToNonExistentUserTest() {
        // Arrange
        int userExistent = 1465;
        int nonExistentUserId = 1010;

        // when-then
        Mockito.when(userRepository.findById(userExistent)).thenReturn(Optional.of(utils.getUSER_1465()));
        Mockito.when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                NotFoundException.class,
                () -> userService.follow(userExistent, nonExistentUserId),
                "User was able to follow a non-existent user"
        );
    }

    @Test
    @DisplayName("Test that given a non-existent user to follow to existent user")
    void nonExistentUserFollowsAnExistentUserTest() {
        // Arrange
        int nonExistentUserId = 1010;
        int userToFollow = 1465;

        // when-then
        Mockito.when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                NotFoundException.class,
                () -> userService.follow(nonExistentUserId, userToFollow),
                "User was able to follow a non-existent user"
        );
    }

    @Test
    @DisplayName("Test to Follow a followed user")
    void followToFollowedUser() {
        // Arrange
        int user = 1465;
        int userToFollow = 4698;

        // when-then
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(utils.getUSER_1465()));
        Mockito.when(userRepository.findById(userToFollow)).thenReturn(Optional.of(utils.getUSER_4698()));

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.follow(user, userToFollow),
                "User was able to follow to himself"
        );
    }

    @Test
    @DisplayName("Test to follow to the same user")
    void followToSameUserTest() {
        // Arrange
        int user = 1115;

        // when-then
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(utils.getUSER_1115()));

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.follow(user, user),
                "User was able to follow to himself"
        );
    }

    @Test
    @DisplayName("Test to follow to null user")
    void followToNullUser() {
        // Arrange
        int user = 1115;

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.follow(user, null),
                "User was able to follow to himself"
        );
    }

    @Test
    public void getTotalFollowersTest(){
        //arrange
        User user = utils.getUSER_1465();
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        UserFollowersDto expected = new UserFollowersDto(user.getUserId(), user.getUserName(), user.getFollowers().size());

        //act
        var result = userService.getTotalFollowers(user.getUserId());

        //assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getTotalFollowersThrowsNotFounExceptionTest(){
        //arrange
        int userId = 10;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //act
        //assert
        Assertions.assertThrows(NotFoundException.class, () -> userService.getTotalFollowers(userId));
    }

}
