package com.socialmeli.socialmeli.mapper;

import com.socialmeli.socialmeli.dto.PostDto;
import com.socialmeli.socialmeli.dto.ProductDto;
import com.socialmeli.socialmeli.entities.Post;
import com.socialmeli.socialmeli.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public PostDto convertPostToDto(Post entity){
        return new PostDto(
                entity.getUserId(),
                entity.getDate(),
                convertProductToDto(entity.getProduct()),
                entity.getCategory(),
                entity.getPrice()
        );
    }

    public Post convertDtoToPost(PostDto postDto){
        Post entity = new Post();
        entity.setUserId(postDto.user_id());
        entity.setDate(postDto.date());
        entity.setProduct(convertDtoToProduct(postDto.product()));
        entity.setCategory(postDto.category());
        entity.setPrice(postDto.price());
        return entity;
    }

    public ProductDto convertProductToDto(Product product){
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getType(),
                product.getBrand(),
                product.getColor(),
                product.getNotes()
        );
    }

    public Product convertDtoToProduct(ProductDto productDto){
        Product product = new Product();
        product.setProductId(productDto.product_id());
        product.setProductName(productDto.product_name());
        product.setType(productDto.type());
        product.setBrand(productDto.brand());
        product.setColor(productDto.color());
        product.setNotes(productDto.notes());
        return product;
    }
}
