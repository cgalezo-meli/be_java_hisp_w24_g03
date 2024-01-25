package com.socialmeli.socialmeli.unit.service;

import com.socialmeli.socialmeli.dto.ResponseDto;
import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.dto.UserFollowedDto;
import com.socialmeli.socialmeli.dto.UserFollowersDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.exceptions.BadRequestException;
import com.socialmeli.socialmeli.exceptions.NotFoundException;
import com.socialmeli.socialmeli.mapper.Mapper;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    Mapper mapper;
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
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(userUtils.getUSER_1465()));
        Mockito.when(userRepository.findById(userToFollow)).thenReturn(Optional.of(userUtils.getUSER_4698()));

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
        Mockito.when(userRepository.findById(user)).thenReturn(Optional.of(userUtils.getUSER_1115()));

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

    @Test
    @DisplayName("Verify that the order 'name_asc' exists")
    public void nameOrderHappyPath() {
        // Arrange
        Integer userId = 4698;
        String order = "name_asc";
        User user = userUtils.getUSER_4698();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act y Assert
        Assertions.assertDoesNotThrow(() -> userService.listFollowed(userId, order),
                "The order name_asc it should exists");
    }

    @Test
    @DisplayName("Verify that the order 'name_desc' exists")
    public void nameDescOrderHappyPath() {
        // Arrange
        Integer userId = 4698;
        String order = "name_desc";
        User user = userUtils.getUSER_4698();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act y Assert
        Assertions.assertDoesNotThrow(() -> userService.listFollowed(userId, order),
                "The order name_desc it should exists");
    }
    @Test
    @DisplayName("Verify that the order name is invalid by throwing an exception")
    public void nameOrderSadPath(){
        // Arrange
        Integer userId = 4698;
        String order = "invalid_order";

        // Act & Assert
        Assertions.assertThrows(BadRequestException.class, () -> userService.listFollowed(userId, order));
    }

    @Test
    @DisplayName("Verify de correct order by name_asc")
    public void listFollowedAscTest(){
        //Arrange
        Integer userId = 4698;
        String order = "name_asc";
        User user4698 = userUtils.getUSER_4698();

        UserFollowedDto expected = new UserFollowedDto(4698,
                "usuario2",
                List.of(new UserDto(1465,
                                "usuario1"),
                        new UserDto(234,
                                "usuario4"),
                        new UserDto(123,
                                "usuario5")));

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user4698));
        Mockito.when(userRepository.listFollowed(userId)).thenReturn(user4698.getFollowed());
        Mockito.when(mapper.convertToUserDtoList(Mockito.anyList())).thenAnswer(list -> {
            List<User> sortedList = list.getArgument(0);
            return sortedList.stream()
                    .map(u -> new UserDto(u.getUserId(), u.getUserName()))
                    .collect(Collectors.toList());
        });

        //Act
        var result = userService.listFollowed(userId,order);

        //Assert
        Assertions.assertEquals(expected,result,"The lists are different.");
    }


    @Test
    @DisplayName("Verify de correct order by name_desc")
    public void listFollowedDescTest(){
        //Arrange
        Integer userId = 4698;
        String order = "name_desc";

        User user = userUtils.getUSER_4698();

        UserFollowedDto expected = new UserFollowedDto(4698,
                "usuario2",
                List.of(new UserDto(123,
                                "usuario5"),
                        new UserDto(234,
                                "usuario4"),
                        new UserDto(1465,
                                "usuario1")));

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.listFollowed(userId)).thenReturn(user.getFollowed());
        Mockito.when(mapper.convertToUserDtoList(Mockito.anyList())).thenAnswer(list -> {
            List<User> sortedList = list.getArgument(0);
            return sortedList.stream()
                    .map(u -> new UserDto(u.getUserId(), u.getUserName()))
                    .collect(Collectors.toList());
        });

        //Act
        var result = userService.listFollowed(userId,order);

        //Assert
        Assertions.assertEquals(expected,result,"The lists are different.");

    }
}
