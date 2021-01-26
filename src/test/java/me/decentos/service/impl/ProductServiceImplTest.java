package me.decentos.service.impl;

import lombok.val;
import me.decentos.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Method should be return a")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService service;

    @DisplayName("list of all products")
    @Order(1)
    @Test
    void getAllProductsTest() {
        val productsList = service.findAllProducts();

        assertNotNull(productsList);
        assertEquals(4, productsList.size());
    }

    @DisplayName("product by ID")
    @Order(2)
    @Test
    void getProductByIdTest() {
        val product = service.findProductById(1);

        assertNotNull(product);
        assertEquals("T-shirt", product.getProductName());
        assertEquals(990.00, product.getPrice());
        assertEquals("2020-10-01T00:00", product.getCreateDate().toString());
        assertNull(product.getUpdateDate());
        assertEquals(0, product.getIsDelete());
    }

    @DisplayName("new product")
    @Order(3)
    @Test
    void createProductTest() {
        service.createProduct("Test create product", 999);
        val productsList = service.findAllProducts();

        assertNotNull(productsList);
        assertEquals(5, productsList.size());
    }

    @DisplayName("product after editing")
    @Order(4)
    @Test
    void updateProductTest() {
        service.updateProduct(1, "Test edit product", 111);
        val product = service.findProductById(1);

        assertNotNull(product);
        assertEquals("Test edit product", product.getProductName());
        assertEquals(111.00, product.getPrice());
        assertEquals("2020-10-01T00:00", product.getCreateDate().toString());
        assertNotNull(product.getUpdateDate());
        assertEquals(0, product.getIsDelete());
    }

    @DisplayName("product after deleting")
    @Order(5)
    @Test
    void deleteProductTest() {
        service.deleteProduct(1);
        val product = service.findProductById(1);
        val productsList = service.findAllProducts();

        assertNotNull(product);
        assertEquals(1, product.getIsDelete());
        assertEquals(4, productsList.size());
    }
}