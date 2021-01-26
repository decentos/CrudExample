package me.decentos.service;

import me.decentos.entity.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> findAllProducts();

    ProductDto findProductById(long id);

    void createProduct(String productName, double price);

    void updateProduct(long id, String productName, double price);

    void deleteProduct(long id);
}
