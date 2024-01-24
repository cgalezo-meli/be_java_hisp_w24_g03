package com.socialmeli.socialmeli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(
        @JsonProperty("userId")
        Integer userId,
        @JsonProperty("userName")
        String userName) {
}
