package com.socialmeli.socialmeli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserFollowerDto(
        @JsonProperty("userId")
        Integer userId,
        @JsonProperty("userName")
        String userName,
        List<UserDto> follower) {
}
