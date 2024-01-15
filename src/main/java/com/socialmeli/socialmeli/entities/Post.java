package com.socialmeli.socialmeli.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int userId;
    private LocalDate date;
    private Product products;
    private Integer category;
    private Double price;
    private Boolean hasProm;
    private Double discount;
}
