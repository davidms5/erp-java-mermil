package com.mermil.erp.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {

    @NotBlank(message = "Product code is required")
    private String cod_Product;

    @NotBlank(message = "Description is required")
    private String descripcion;

    @NotNull(message = "Price is required")
    private int precio;

    private String Proveedor;
    private Integer precioCompra;

    @Override
    public String toString() {
        return "cod_Product='" + cod_Product + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", Proveedor='" + Proveedor + '\'' +
                ", precioCompra=" + precioCompra;
    }
}
