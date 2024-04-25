package com.mermil.erp.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.mermil.erp.services.StageNavigationServices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Component
public class MainScreenController implements Initializable {

    @FXML
    private VBox leftVBox;

    @FXML
    private Label ventasLabel;

    @FXML
    private Pane rightPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You can initialize anything here if needed
    }

    @FXML
    protected void handleLogout(ActionEvent event) {
        if (!StageNavigationServices.arePopupsOpen()) {
            StageNavigationServices.ChangeScene(event, "/Login.fxml", "login");
        } else {
            // Show an alert informing the user that they cannot logout while popup screens
            // are open
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Debe cerrar primero las ventanas abiertas");
            alert.showAndWait();
        }

    }

    @FXML
    protected void handleReciboPopupScreen(ActionEvent event) {
        // me parece que no hace falta pasarle el event
        StageNavigationServices.showPopupScreen(event, "/views/Recibos.fxml", "recibo");
    }

    // STOCK sub menu #note this can be encapsulated
    @FXML
    protected void handleStockButton(ActionEvent event) {
        // Clear the right pane before adding new content
        rightPane.getChildren().clear();

        // Create stock-related options
        Button option1 = new Button("agregar stock");

        option1.setOnAction(
                e -> StageNavigationServices.showPopupScreen(event, "/views/ProductosScreen.fxml", "stock"));
        option1.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 14px;");

        Button option2 = new Button("Option 2");
        option2.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 14px;");
        // Add action handlers to the options if needed
        VBox buttonBox = new VBox(10); // 10 is spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(option1, option2);

        // Add options to the right pane
        rightPane.getChildren().add(buttonBox);
    }
}
