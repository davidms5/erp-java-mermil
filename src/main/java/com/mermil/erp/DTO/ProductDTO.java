package com.mermil.erp.DTO;

import java.math.BigDecimal;

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
    private BigDecimal precio;

    @NotNull(message = "precio venta es requerido")
    private BigDecimal precio_venta;

    private String Proveedor;
    private BigDecimal precioCompra;

    @Override
    public String toString() {
        return "cod_Product='" + cod_Product + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", precio_venta=" + precio_venta +
                ", Proveedor='" + Proveedor + '\'' +
                ", precioCompra=" + precioCompra;
    }
}
