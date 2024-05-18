package com.mermil.erp.controllers.stock;

import java.io.File;
import java.math.BigDecimal;

//import org.springframework.beans.factory.annotation.Autowired;

import com.mermil.erp.ErpApplication;
import com.mermil.erp.DTO.ProductDTO;
//import com.mermil.erp.factory.ProductServiceFactory;
//import com.mermil.erp.DTO.ProductDTO;
import com.mermil.erp.services.businessLogic.ProductService;

import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

public class UploadStockController {

    private final ProductService productService;

    private final EntityManagerFactory entityManagerFactory;

    public UploadStockController() {
        this.entityManagerFactory = ErpApplication.getEntityManagerFactory();
        // ProductServiceFactory productServiceFactory = new
        // ProductServiceFactory(entityManagerFactory);
        this.productService = new ProductService(entityManagerFactory);
    }

    @FXML
    private TextField codProductTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField sellPriceField;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Label statusLabel;

    @FXML
    private ListView<String> productListView;

    @FXML
    private void handleAddButtonClick() {
        // Implement logic to add a product
        statusLabel.setText("");

        String codigoProducto = codProductTextField.getText();
        String descripcion = descriptionTextField.getText();
        String precio = priceTextField.getText();
        // String precioVenta = sellPriceField.getText();

        // Check if any text field is empty
        if (codigoProducto.isEmpty() || descripcion.isEmpty() || precio.isEmpty() /* || precioVenta.isEmpty() */) {
            statusLabel.setText("Todos los campos deben ser llenados.");
            return;
        }

        try {
            BigDecimal price = new BigDecimal(precio);
            // BigDecimal sellPrice = new BigDecimal(precioVenta);

            // Create a map and add the product details
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("codProduct", codigoProducto);
            productMap.put("descripcion", descripcion);
            productMap.put("precio", price);
            // productMap.put("precioVenta", sellPrice);

            // You can now use productMap as needed, for example:
            // Add to product list, upload, etc.

            productService.createProduct(productMap);
            // Clear the text fields after adding the product
            codProductTextField.clear();
            descriptionTextField.clear();
            priceTextField.clear();
            // sellPriceField.clear();

            statusLabel.setText("Producto agregado exitosamente.");

        } catch (NumberFormatException e) {
            statusLabel.setText("Los campos de precio deben contener números válidos.");
        }

    }

    @FXML
    private void handleUpdateButtonClick() {
        // Implement logic to update a product
    }

    @FXML
    private void handleDeleteButtonClick() {
        // Implement logic to delete a product
    }

    @FXML
    private void handleFindProductById() {

        statusLabel.setText("");

        String codigo_producto = codProductTextField.getText();

        if (codigo_producto.length() == 0) {

            statusLabel.setText("un codigo necesita ser pasado");
            return;
        }
        ;

        Map<String, Object> product = productService.getProductBy_cod_product(codigo_producto);
        if (product == null) {
            statusLabel.setText(String.format("el producto con el codigo: %s no existe", codigo_producto));
            return;
        }

        descriptionTextField.setText((String) product.get("descripcion"));

        BigDecimal price = (BigDecimal) product.get("precio");
        priceTextField.setText(price.toString());

    }

    @FXML
    private void handleUploadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Excel or CSV file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel or CSV files", "*.xlsx", "*.xls",
                "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {

            try {
                /* List<ProductDTO> products = */
                productService.bulkPostProducts(file);
                statusLabel.setText("Bulk post successful!");
            } catch (Exception e) {
                statusLabel.setText("Error occurred during bulk post: " + e.getMessage());
            }

        } else {
            statusLabel.setText("No file selected.");
        }
    }

    @FXML
    private void initialize() {
        displayAllProducts();
    }

    private void displayAllProducts() {
        List<ProductDTO> productList = productService.getAllProducts();
        List<String> productStrings = productList.stream().map(ProductDTO::toString).collect(Collectors.toList());
        ObservableList<String> products = FXCollections.observableArrayList(productStrings);
        productListView.setItems(products);
    }
}
