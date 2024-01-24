package com.socialmeli.socialmeli.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDto(
        @JsonProperty("product_id")
        Integer productId,
        @JsonProperty("product_name")
        String productName,
        String type,
        String  brand,
        String color,
        String notes) {
}
