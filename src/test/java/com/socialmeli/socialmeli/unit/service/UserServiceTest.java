package com.socialmeli.socialmeli.unit.service;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.dto.UserFollowersDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.exceptions.BadRequestException;
import com.socialmeli.socialmeli.exceptions.NotFoundException;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import com.socialmeli.socialmeli.services.UserService;
import com.socialmeli.socialmeli.utils.UserUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    UserUtils userUtils = new UserUtils();

    @Test
    @DisplayName("Test to follow an existing user")
    void followToExistentUserTest() {
        // Arrange
        int existentUserId = 1115;
        int followed = 1465;
        ResponseDto expected = new ResponseDto("Follow exitoso");

        // when-then
        Mockito.when(userRepository.findById(followed)).thenReturn(Optional.of(userUtils.getUSER_1465()));
        Mockito.when(userRepository.findById(existentUserId)).thenReturn(Optional.of(userUtils.getUSER_1115()));

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
        Mockito.when(userRepository.findById(userExistent)).thenReturn(Optional.of(userUtils.getUSER_1465()));
        Mockito.when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                NotFoundException.class,
                () -> userService.follow(userExistent, nonExistentUserId),
                "User was able to follow a non-existent user"
        );
    }

    @Test
    @DisplayName("Test that given a non-existent user to follow an existent user")
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
                "Non-existent user was able to follow an existent user"
        );
    }

    @Test
    @DisplayName("Test to follow a followed user")
    void followToFollowedUser() {
        // Arrange
        int user = 1465;
        int userToFollow = 4698;

        // when-then
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(userUtils.getUSER_1465()));
        Mockito.when(userRepository.findById(userToFollow)).thenReturn(Optional.of(userUtils.getUSER_4698()));

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.follow(user, userToFollow),
                "User was able to follow a followed user"
        );
    }

    @Test
    @DisplayName("Test to follow to the same user")
    void followToSameUserTest() {
        // Arrange
        int user = 1115;

        // when-then
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(userUtils.getUSER_1115()));

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.follow(user, user),
                "User was able to follow to himself"
        );
    }

    @Test
    @DisplayName("Test to follow a null user")
    void followToNullUser() {
        // Arrange
        int user = 1115;

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.follow(user, null),
                "User was able to follow a null user"
        );
    }

    @Test
    @DisplayName("Test to unfollow an existing user")
    void unfollowToExistentUserTest() {
        // Arrange
        int follower = 1465;
        int userToUnfollow = 4698;
        ResponseDto expected = new ResponseDto("Unfollow exitoso");

        // when-then
        Mockito.when(userRepository.findById(follower)).thenReturn(Optional.of(userUtils.getUSER_1465()));
        Mockito.when(userRepository.findById(userToUnfollow)).thenReturn(Optional.of(userUtils.getUSER_4698()));

        // Act
        ResponseDto result =userService.unfollow(follower, userToUnfollow);

        // Assert
        Assertions.assertEquals(expected, result, "It was not possible to unfollow the user");
    }

    @Test
    @DisplayName("Test to unfollow a non-existent user")
    void unfollowToNonExistentUserTest() {
        // Arrange
        int existentFollower = 1465;
        int nonExistentUserToIUnfollow = 1010;

        // when-then
        Mockito.when(userRepository.findById(existentFollower)).thenReturn(Optional.of(userUtils.getUSER_1465()));
        Mockito.when(userRepository.findById(nonExistentUserToIUnfollow)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                NotFoundException.class,
                () -> userService.unfollow(existentFollower, nonExistentUserToIUnfollow),
                "User was able to unfollow a non-existent user"
        );
    }

    @Test
    @DisplayName("Test that given a non-existent user to unfollow an existent user")
    void nonExistentUserUnfollowsAnExistentUserTest() {
        // Arrange
        int nonExistentFollower = 1010;
        int userToUnfollow = 1465;

        // when-then
        Mockito.when(userRepository.findById(nonExistentFollower)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(
                NotFoundException.class,
                () -> userService.unfollow(nonExistentFollower, userToUnfollow),
                "Non-existent user was able to unfollow an user"
        );
    }

    @Test
    @DisplayName("Test to unfollow a non-followed user")
    void unfollowToUnfollowedUser() {
        // Arrange
        int follower = 1465;
        int userToUnfollow = 1115;

        // when-then
        Mockito.when(userRepository.findById(follower)).thenReturn(Optional.of(userUtils.getUSER_1465()));
        Mockito.when(userRepository.findById(userToUnfollow)).thenReturn(Optional.of(userUtils.getUSER_1115()));

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.unfollow(follower, userToUnfollow),
                "User was able to unfollow a non-followed user"
        );
    }

    @Test
    @DisplayName("Test to follow to the same user")
    void unfollowToSameUserTest() {
        // Arrange
        int user = 1115;

        // when-then
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(userUtils.getUSER_1115()));

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.unfollow(user, user),
                "User was able to unfollow to himself"
        );
    }

    @Test
    @DisplayName("Test to follow a null user")
    void unfollowToNullUser() {
        // Arrange
        int user = 1115;

        // Act & Assert
        Assertions.assertThrows(
                BadRequestException.class,
                () -> userService.unfollow(user, null),
                "User was able to unfollow a null user"
        );
    }

    @Test
    @DisplayName("Test to get total followers")
    public void getTotalFollowersTest(){
        //arrange
        User user = userUtils.getUSER_1465();
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        UserFollowersDto expected = new UserFollowersDto(user.getUserId(), user.getUserName(), user.getFollowers().size());

        //act
        var result = userService.getTotalFollowers(user.getUserId());

        //assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test to get a Not Found Exception when not found an existent user")
    public void getTotalFollowersThrowsNotFounExceptionTest(){
        //arrange
        int userId = 10;
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //act & assert
        Assertions.assertThrows(NotFoundException.class, () -> userService.getTotalFollowers(userId));
    }

}
