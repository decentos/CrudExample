package me.decentos.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.decentos.entity.dto.ProductDto;
import me.decentos.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/crud/example")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    @ApiOperation(value = "Method for getting all products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        try {
            return ResponseEntity.ok(productService.findAllProducts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/products/{id}")
    @ApiOperation(value = "Method for getting product by ID")
    public ResponseEntity<ProductDto> getProduct(
            @ApiParam(name = "id", value = "Product ID", required = true) @PathVariable("id") long id
    ) {
        try {
            return ResponseEntity.ok(productService.findProductById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    @ApiOperation(value = "Method for creating a new product")
    public ResponseEntity<?> createProduct(
            @ApiParam(name = "productName", value = "Name for new product", required = true) @RequestParam String productName,
            @ApiParam(name = "price", value = "Price for product", required = true) @RequestParam double price
    ) {
        try {
            productService.createProduct(productName, price);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/products/{id}")
    @ApiOperation(value = "Method for updating product by ID")
    public ResponseEntity<?> updateProduct(
            @ApiParam(name = "id", value = "Product ID", required = true) @PathVariable("id") long id,
            @ApiParam(name = "productName", value = "Name for new product") @RequestParam(required = false) String productName,
            @ApiParam(name = "price", value = "Price for product") @RequestParam(required = false, defaultValue = "0") double price
    ) {
        try {
            productService.updateProduct(id, productName, price);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/products/{id}")
    @ApiOperation(value = "Method for deleting product by ID")
    public ResponseEntity<?> deleteProduct(
            @ApiParam(name = "id", value = "Product ID", required = true) @PathVariable("id") long id
    ) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
