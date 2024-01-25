package com.socialmeli.socialmeli;

import com.socialmeli.socialmeli.dto.UserFollowersDto;
import com.socialmeli.socialmeli.entities.User;
import com.socialmeli.socialmeli.exceptions.NotFoundException;
import com.socialmeli.socialmeli.repositories.IUserRepository;
import com.socialmeli.socialmeli.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private IUserRepository repository;

    @InjectMocks
    private UserService service;

    private User user = new User(1465, "usuario1", List.of(new User(4698, "usuario2", null, null)), List.of(new User(4698, "usuario2", null, null)));

    @Test
    public void getTotalFollowersTest(){
        //arrange
        Mockito.when(repository.findById(user.getUserId())).thenReturn(Optional.of(user));
        UserFollowersDto expected = new UserFollowersDto(user.getUserId(), user.getUserName(), user.getFollowers().size());

        //act
        var result = service.getTotalFollowers(user.getUserId());

        //assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getTotalFollowersThrowsNotFounExceptionTest(){
        //arrange
        int userId = 10;
        Mockito.when(repository.findById(userId)).thenReturn(Optional.empty());

        //act
        //assert
        Assertions.assertThrows(NotFoundException.class, () -> service.getTotalFollowers(userId));
    }


}