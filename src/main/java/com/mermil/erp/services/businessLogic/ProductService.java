package com.mermil.erp.services.businessLogic;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import jakarta.persistence.EntityTransaction;
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

    public ProductDTO getProductBy_cod_product(String codProduct) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ProductDTO> criteriaQuery = criteriaBuilder.createQuery(ProductDTO.class);
            Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
            criteriaQuery.select(criteriaBuilder.construct(ProductDTO.class,
                    root.get("cod_product"),
                    root.get("descripcion"),
                    root.get("precio"),
                    root.get("precio_venta")
            // Add other fields you need here...
            )).where(criteriaBuilder.equal(root.get("cod_product"), codProduct));
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    public ProductDTO createProduct(ProductDTO dto) {
        // Convert DTO to entity
        ProductModel entity = convertToEntity(dto);

        // Save entity in the database
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
        entityManager.close();

        // Convert entity back to DTO and return
        return convertToDTO(entity);
    }

    private ProductDTO convertToDTO(ProductModel product) {
        ProductDTO dto = new ProductDTO();
        dto.setCod_Product(product.getCod_product());
        dto.setDescripcion(product.getDescripcion());
        dto.setPrecio(product.getPrecio());
        dto.setPrecio_venta(product.getPrecio_venta());
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
                        dto.setPrecio(new BigDecimal(0));
                        // Process string value
                        break;
                    case NUMERIC:
                        dto.setPrecio(new BigDecimal(Double.toString(row.getCell(3).getNumericCellValue())));
                        // Process numeric value
                        break;
                    default:
                        break;
                    // Handle other cell types if needed
                }
                // System.out.println(row.getCell(4).getCellType());
                switch (row.getCell(4).getCellType()) {
                    case STRING, BLANK:
                        dto.setPrecio_venta(new BigDecimal(0));
                        // Process string value
                        break;
                    case NUMERIC:
                        dto.setPrecio_venta(new BigDecimal(Double.toString(row.getCell(4).getNumericCellValue())));
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
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            List<ProductModel> existingEntities = getExistingEntities(entityManager, productList);
            for (ProductDTO dto : productList) {
                ProductModel entity = findEntityByCodProduct(existingEntities, dto.getCod_Product());
                if (entity != null) {
                    // Update existing entity
                    updateEntity(entity, dto);
                } else {
                    // Insert new entity
                    entity = convertToEntity(dto);
                    entityManager.persist(entity);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }

        return "ok";
    }

    private List<ProductModel> getExistingEntities(EntityManager entityManager, List<ProductDTO> productList) {

        Set<String> codProducts = productList.stream().map(ProductDTO::getCod_Product).collect(Collectors.toSet());
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductModel> criteriaQuery = criteriaBuilder.createQuery(ProductModel.class);
        Root<ProductModel> root = criteriaQuery.from(ProductModel.class);
        criteriaQuery.select(root)
                .where(root.get("cod_product").in(codProducts));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private ProductModel findEntityByCodProduct(List<ProductModel> entities, String codProduct) {
        return entities.stream()
                .filter(entity -> entity.getCod_product().equals(codProduct))
                .findFirst()
                .orElse(null);
    }

    private void updateEntity(ProductModel entity, ProductDTO dto) {
        // Update entity fields
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setProveedor(dto.getProveedor());
        entity.setPrecio_compra(dto.getPrecioCompra());
        entity.setPrecio_venta(dto.getPrecio_venta());
    }

    private ProductModel convertToEntity(ProductDTO dto) {
        ProductModel entity = new ProductModel();
        entity.setCod_product(dto.getCod_Product());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setProveedor(dto.getProveedor());
        entity.setPrecio_compra(dto.getPrecioCompra());
        entity.setPrecio_venta(dto.getPrecio_venta());
        return entity;
    }
}
