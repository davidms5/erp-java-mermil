package com.mermil.erp.controllers.stock;

import java.io.File;

//import com.mermil.erp.DTO.ProductDTO;
import com.mermil.erp.services.businessLogic.ProductService;
//import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploadStockController {

    @FXML
    private TextField filePathTextField;

    @FXML
    private Button chooseFileButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Label statusLabel;

    private ProductService productService;

    public UploadStockController() {
        productService = new ProductService(); // Initialize your ProductService
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
}
