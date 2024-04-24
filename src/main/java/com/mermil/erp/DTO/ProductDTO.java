package com.mermil.erp.DTO;

import lombok.Data;

@Data
public class ProductDTO {

    private String codProduct;
    private String description;
    private int price;
    private String provider;
    private Integer purchasePrice;
}
