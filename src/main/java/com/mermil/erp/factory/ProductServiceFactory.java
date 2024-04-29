package com.mermil.erp.factory;

import com.mermil.erp.services.businessLogic.ProductService;

import jakarta.persistence.EntityManagerFactory;

public class ProductServiceFactory {

    private final EntityManagerFactory entityManagerFactory;

    public ProductServiceFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public ProductService createProductService() {
        return new ProductService(entityManagerFactory);
    }
}
