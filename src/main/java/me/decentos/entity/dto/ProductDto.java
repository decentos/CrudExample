package me.decentos.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.decentos.entity.model.Product;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productName;
    private double price;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private int isDelete;

    public static ProductDto convertToDto(Product product) {
        return new ProductDto(product.getProductName(), product.getPrice(), product.getCreateDate(), product.getUpdateDate(), product.getIsDelete());
    }
}
