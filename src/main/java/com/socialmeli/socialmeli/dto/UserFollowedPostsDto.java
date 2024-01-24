package com.socialmeli.socialmeli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserFollowedPostsDto(
        @JsonProperty("userId")
        Integer userId,
        List<PostDto> posts) {
}
