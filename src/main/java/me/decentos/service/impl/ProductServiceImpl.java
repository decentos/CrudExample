package me.decentos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.decentos.entity.dto.ProductDto;
import me.decentos.entity.model.Product;
import me.decentos.repository.ProductRepository;
import me.decentos.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public List<ProductDto> findAllProducts() {
        return repository
                .findAll()
                .stream()
                .filter(product -> product.getIsDelete() == 0)
                .map(ProductDto::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findProductById(long id) {
        return ProductDto.convertToDto(repository.findById(id));
    }

    @Override
    public void createProduct(String productName, double price) {
        Product product = new Product(productName, price, LocalDateTime.now());
        repository.save(product);
        log.info("Successfully save a new product: {}", product.getProductName());
    }

    @Override
    public void updateProduct(long id, String productName, double price) {
        Product product = repository.findById(id);
        if (productName != null) product.setProductName(productName);
        if (price != 0) product.setPrice(price);
        product.setUpdateDate(LocalDateTime.now());
        repository.save(product);
        log.info("Successfully update product: {}", product.getProductName());
    }

    @Override
    public void deleteProduct(long id) {
        Product product = repository.findById(id);
        product.setIsDelete(1);
        product.setUpdateDate(LocalDateTime.now());
        repository.save(product);
        log.info("Product - {}, id - {} was marked as deleted", product.getProductName(), product.getId());
    }
}
