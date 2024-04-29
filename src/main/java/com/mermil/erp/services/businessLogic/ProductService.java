package com.mermil.erp.services.businessLogic;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.FileInputStream;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mermil.erp.DTO.ProductDTO;
import com.mermil.erp.models.ProductModel;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class ProductService {

    private final EntityManagerFactory entityManagerFactory;

    public ProductService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<ProductDTO> getAllProducts() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ProductModel> criteriaQuery = criteriaBuilder.createQuery(ProductModel.class);
            Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
            criteriaQuery.select(root);
            List<ProductModel> products = entityManager.createQuery(criteriaQuery).getResultList();
            return products.stream().map(this::convertToDTO).collect(Collectors.toList());
        } finally {
            entityManager.close();
        }
    }

    private ProductDTO convertToDTO(ProductModel product) {
        ProductDTO dto = new ProductDTO();
        dto.setCod_Product(product.getCod_product());
        dto.setDescripcion(product.getDescripcion());
        dto.setPrecio(product.getPrecio());
        dto.setProveedor(product.getProveedor());
        dto.setPrecioCompra(product.getPrecio_compra());
        return dto;
    }

    public String bulkPostProducts(File file) throws IOException {
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
                        dto.setPrecio(0);
                        // Process string value
                        break;
                    case NUMERIC:
                        dto.setPrecio((int) row.getCell(3).getNumericCellValue());
                        // Process numeric value
                        break;
                    default:
                        break;
                    // Handle other cell types if needed
                }
                productList.add(dto);
            }
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            for (ProductDTO dto : productList) {
                ProductModel entity = convertToEntity(dto);
                entityManager.persist(entity);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

        return "ok";
    }

    private ProductModel convertToEntity(ProductDTO dto) {
        ProductModel entity = new ProductModel();
        entity.setCod_product(dto.getCod_Product());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setProveedor(dto.getProveedor());
        entity.setPrecio_compra(dto.getPrecioCompra());
        return entity;
    }
}
