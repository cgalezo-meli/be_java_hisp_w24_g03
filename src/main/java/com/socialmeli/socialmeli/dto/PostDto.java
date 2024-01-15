package com.socialmeli.socialmeli.dto;

import java.time.LocalDate;

public record PostDto(Integer user_id, LocalDate date, ProductDto product, Integer category, Double price) {
}
