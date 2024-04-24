package com.mermil.erp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String cod_product;
    private String descripcion;
    private int precio;

    @Column(nullable = true)
    private String proveedor;

    @Column(nullable = true)
    private Integer precio_compra;

}
