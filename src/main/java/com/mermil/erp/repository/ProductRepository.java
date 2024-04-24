package com.mermil.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mermil.erp.models.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

}
