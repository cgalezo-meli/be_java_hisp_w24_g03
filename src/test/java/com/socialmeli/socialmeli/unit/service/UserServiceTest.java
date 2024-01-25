package com.socialmeli.socialmeli.unit.service;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.exceptions.BadRequestException;
import com.socialmeli.socialmeli.exceptions.NotFoundException;
import com.socialmeli.socialmeli.repositories.UserRepositoryImpl;
import com.socialmeli.socialmeli.services.UserService;
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

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private final User USER_1465 = new User(
            1465,
            "usuario1",
            new ArrayList<>(
                    List.of(
                            new User(
                                    4698,
                                    "usuario2",
                                    null,
                                    null
                            )
                    )
            ),
            new ArrayList<>(
                    List.of(
                            new User(
                                    4698,
                                    "usuario2",
                                    null,
                                    null
                            )
                    )
            )
    );
    private final User USER_1115 = new User(
            1115,
            "usuario3",
            new ArrayList<>(),
            new ArrayList<>(
                    List.of(
                            new User(
                                    4698,
                                    "usuario2",
                                    null,
                                    null
                            )
                    )
            )
    );
    private final User USER_4698 = new User(
            4698,
            "usuario2",
            new ArrayList<>(),
            new ArrayList<>()
    );

    @Mock
    private UserRepositoryImpl userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Test to follow an existing user")
    void followToExistentUserTest() {
        // Arrange
        int existentUserId = 1115;
        int followed = 1465;
        ResponseDto expected = new ResponseDto("Follow exitoso");

        // when-then
        Mockito.when(userRepository.findById(followed)).thenReturn(Optional.of(USER_1465));
        Mockito.when(userRepository.findById(existentUserId)).thenReturn(Optional.of(USER_1115));

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
        Mockito.when(userRepository.findById(userExistent)).thenReturn(Optional.of(USER_1465));
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
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(USER_1465));
        Mockito.when(userRepository.findById(userToFollow)).thenReturn(Optional.of(USER_4698));

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
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(USER_1115));

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
}
