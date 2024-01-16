package com.socialmeli.socialmeli.dto;

import java.util.List;

public record UserFollowedDto(Integer userId, String userName, List<UserDto> followed) {
}
