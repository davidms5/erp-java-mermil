package com.mermil.erp.services.businessLogic;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mermil.erp.DTO.ProductDTO;
import com.mermil.erp.models.ProductModel;
import com.mermil.erp.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {

        List<ProductModel> products = productRepository.findAll();

        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(ProductModel product) {
        ProductDTO dto = new ProductDTO();
        dto.setCodProduct(product.getCod_Product());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setSupplier(product.getSupplier());
        dto.setPurchasePrice(product.getPurchasePrice());
        return dto;
    }
}
