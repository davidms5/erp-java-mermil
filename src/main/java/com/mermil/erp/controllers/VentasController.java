package com.mermil.erp.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

@Controller
public class VentasController {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField idField;

    @FXML
    private TextField senorField;

    @FXML
    private TextField direccionField;

    @FXML
    private TextField cuitField;

    @FXML
    private ChoiceBox<String> ivaTipoChoiceBox;

    @FXML
    private void cancelClienteData() {
        // Clear the data of the client
        idField.clear();
        senorField.clear();
        direccionField.clear();
        cuitField.clear();
        ivaTipoChoiceBox.getSelectionModel().clearSelection();
    }

    public void initialize() {

        idField.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
            // Allow only numeric characters
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume();
            }
        });
        // Set the initial value of the DatePicker to today
        datePicker.setValue(LocalDate.now());

        // Set the end date of the DatePicker to one week from now
        datePicker.setShowWeekNumbers(true);
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(date.isBefore(today) || date.isAfter(today.plusWeeks(3)));
            }
        });
    }
}
