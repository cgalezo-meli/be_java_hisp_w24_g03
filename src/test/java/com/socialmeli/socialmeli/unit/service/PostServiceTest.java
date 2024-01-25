package com.socialmeli.socialmeli.unit.service;

import com.socialmeli.socialmeli.dto.PostDto;
import com.socialmeli.socialmeli.dto.ProductDto;
import com.socialmeli.socialmeli.dto.UserDto;
import com.socialmeli.socialmeli.dto.UserFollowedPostsDto;
import com.socialmeli.socialmeli.entities.Post;
import com.socialmeli.socialmeli.entities.Product;
import com.socialmeli.socialmeli.exceptions.NotFoundException;
import com.socialmeli.socialmeli.mapper.Mapper;
import com.socialmeli.socialmeli.repositories.PostRepositoryImpl;
import com.socialmeli.socialmeli.services.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepositoryImpl postRepository;

    @Mock
    Mapper mapper;

    @InjectMocks
    private PostService postService;

    // Constantes
    Post postId1 = new Post(1465,
            1,
            LocalDate.of(2024, 01, 02),
            new Product(1, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition"),
            100,
            1500.50);

    Post postId2 = new Post(1465,
            2,
            LocalDate.of(2023, 12, 30),
            new Product(1, "Headset RGB Inalámbrico", "Gamer", "Razer", "Green with RGB", "Sin Batería"),
            100,
            1500.50);

    Post postId7 = new Post(234,
            7,
            LocalDate.of(2024, 01, 16),
            new Product(7, "Mochila", "", "Everlast", "Blue", "Permite Notebook"),
            100,
            8000.50);

    PostDto postDtoId1 = new PostDto(1465,
            LocalDate.of(2024, 01, 02),
            new ProductDto(1, "Silla Gamer", "Gamer", "Racer", "Red & Black", "Special Edition"),
            100,
            1500.50);

    PostDto postDtoId2 = new PostDto(1465,
            LocalDate.of(2023, 12, 30),
            new ProductDto(1, "Headset RGB Inalámbrico", "Gamer", "Razer", "Green with RGB", "Sin Batería"),
            100,
            1500.50);

    PostDto postDtoId7 = new PostDto(234,
            LocalDate.of(2024, 01, 16),
            new ProductDto(7, "Mochila", "", "Everlast", "Blue", "Permite Notebook"),
            100,
            8000.50);


    @Test
    @DisplayName("getLastTwoWeeksFollowedPosts: should return UserFollowedPostsDto")
    public void getLastTwoWeeksFollowedPosts(){
        //Arrange
        Integer userId = 4698;
        String order = "date_desc";
        List<UserDto> followedList = List.of(
                new UserDto(1465, "usuario1"),
                new UserDto(234, "usuario4"),
                new UserDto(123, "usuario5")
        );

        ArrayList<Post> allPosts = new ArrayList<>(Arrays.asList(postId1, postId2, postId7));

        List<PostDto> posts = List.of(postDtoId7);

        UserFollowedPostsDto expected = new UserFollowedPostsDto(userId, posts);

        //Act
        Mockito.when(this.postRepository.findAll()).thenReturn(allPosts);
        Mockito.when(mapper.convertPostToDto(postId1)).thenReturn(postDtoId1);
        Mockito.when(mapper.convertPostToDto(postId2)).thenReturn(postDtoId2);
        Mockito.when(mapper.convertPostToDto(postId7)).thenReturn(postDtoId7);
        var result = postService.getLastTwoWeeksFollowedPosts(userId, followedList, order);

        //Assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("getLastTwoWeeksFollowedPosts: should return NotFoundException when followedList is empty")
    public void followedListEmptyGetLastTwoWeeksFollowedPosts(){
        //Arrange
        Integer userId = 234;
        String order = "date_desc";
        List<UserDto> followedList = new ArrayList<>();

        //Assert
        Assertions.assertThrows(NotFoundException.class, () ->{
            postService.getLastTwoWeeksFollowedPosts(userId, followedList, order);
        });
    }

    @Test
    @DisplayName("getLastTwoWeeksFollowedPosts: should return NotFoundException when allFollowedPosts is empty")
    public void allFollowedPostsEmptyGetLastTwoWeeksFollowedPosts(){
        //Arrange
        Integer userId = 4698;
        String order = "date_desc";
        List<UserDto> followedList = new ArrayList<>();

        //Assert
        Assertions.assertThrows(NotFoundException.class, () ->{
            postService.getLastTwoWeeksFollowedPosts(userId, followedList, order);
        });
    }
}
