package com.mermil.erp.controllers.stock;

import java.io.File;

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
