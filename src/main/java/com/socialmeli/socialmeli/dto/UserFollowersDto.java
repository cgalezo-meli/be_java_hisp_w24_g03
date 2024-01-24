package com.socialmeli.socialmeli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserFollowersDto(
        @JsonProperty("userId")
        Integer userId,
        @JsonProperty("userName")
        String userName, Integer followers_count) {
}