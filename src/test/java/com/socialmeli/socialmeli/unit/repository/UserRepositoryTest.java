package com.socialmeli.socialmeli.unit.repository;

import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import com.socialmeli.socialmeli.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryTest {
    private final User NEW_USER = new User(
            1117,
            "usuario1",
            new ArrayList<>(),
            new ArrayList<>()
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

    IUserRepository userRepositor = new UserRepositoryImpl();

    @Test
    @DisplayName("Test to add a new user")
    void saveTest(){
        // Act
        var result = userRepositor.save(NEW_USER);

        // Assert
        Assertions.assertEquals(NEW_USER, result, "User was not saved");
    }

    @Test
    @DisplayName("Test to update an user")
    void updateTest(){
        // Arrange
        User expected = USER_1115;
        expected.setUserName("User 1115");

        // Act
        var result = userRepositor.update(expected);

        // Assert
        Assertions.assertEquals(expected, result, "User was not updated");
    }

    @Test
    @DisplayName("Test to delete an user")
    void deleteTest(){
        // Arrange
        int idParam = 1115;

        // Act
        var result = userRepositor.deleteById(idParam);

        // Assert
        Assertions.assertTrue(result, "User was not delete");
    }

    @Test
    @DisplayName("Test to find an user by id")
    void findByIdTest(){
        // Arrange
        int idParam = 1115;
        Optional<User> expected = Optional.of(USER_1115);

        // Act
        var result = userRepositor.findById(idParam);

        // Assert
        Assertions.assertEquals(expected, result, "User was not found");
    }

    @Test
    @DisplayName("Test to find all user")
    void findAllTest(){
        // Arrange
        int expected = 2;

        // Act
        int result = userRepositor.findAll().size();

        // Assert
        Assertions.assertEquals(expected, result, "Users were not found");
    }

    @Test
    @DisplayName("Test to find a followed list by id")
    void listFollowedTest(){
        // Arrange
        int idParam = 1115;
        List<User> expected = USER_1115.getFollowed();

        // Act
        var result = userRepositor.listFollowed(idParam);

        // Assert
        Assertions.assertEquals(expected, result, "List was not found");
    }

    @Test
    @DisplayName("Test to find a followed list by non-existent id")
    void listFollowedByNonExistentIdTest(){
        // Arrange
        int idParam = 1010;
        List<User> expected = new ArrayList<>();

        // Act
        var result = userRepositor.listFollowed(idParam);

        // Assert
        Assertions.assertEquals(expected, result, "List was not found");
    }
}
