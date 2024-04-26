package com.mermil.erp.services.businessLogic;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileInputStream;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
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
        dto.setCod_Product(product.getCod_product());
        dto.setDescripcion(product.getDescripcion());
        dto.setPrice(product.getPrecio());
        dto.setProveedor(product.getProveedor());
        dto.setPrecioCompra(product.getPrecio_compra());
        return dto;
    }

    public void bulkPostProducts(File file) throws IOException {
        List<ProductDTO> productList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(file))) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                ProductDTO dto = new ProductDTO();
                Row row = sheet.getRow(i);
                dto.setCod_Product(row.getCell(1).getStringCellValue());
                dto.setDescripcion(row.getCell(2).getStringCellValue());

                switch (row.getCell(3).getCellType()) {
                    case STRING, BLANK:
                        dto.setPrice(0);
                        // Process string value
                        break;
                    case NUMERIC:
                        dto.setPrice((int) row.getCell(3).getNumericCellValue());
                        // Process numeric value
                        break;
                    default:
                        break;
                    // Handle other cell types if needed
                }
                productList.add(dto);
            }
        }

        List<ProductModel> productModels = productList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
        productRepository.saveAll(productModels);

        // return productList;
    }

    private ProductModel convertToEntity(ProductDTO dto) {
        ProductModel entity = new ProductModel();
        entity.setCod_product(dto.getCod_Product());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrice());
        entity.setProveedor(dto.getProveedor());
        entity.setPrecio_compra(dto.getPrecioCompra());
        return entity;
    }
}
