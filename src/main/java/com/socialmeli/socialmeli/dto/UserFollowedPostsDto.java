package com.socialmeli.socialmeli.dto;

import java.util.List;

public record UserFollowedPostsDto(Integer userId, List<PostDto> followedPosts) {
}
