package com.mermil.erp.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.mermil.erp.services.StageNavigationServices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

@Component
public class MainScreenController implements Initializable {

    @FXML
    private VBox leftVBox;

    @FXML
    private Label ventasLabel;

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
}
