package com.mermil.erp.controllers;

import java.io.IOException;

import org.springframework.stereotype.Controller;

import com.mermil.erp.services.StageNavigationServices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void handleLogin(ActionEvent event) throws IOException {
        // Implement your login logic here
        boolean correctUser = usernameField.getText().equals("admin") && passwordField.getText().equals("admin");

        if (correctUser) {

            StageNavigationServices.ChangeScene(event, "/views/MainScreen.fxml", "Home");
        }
    }
}
