package com.mermil.erp.controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

@Controller
public class VentasController {

    @FXML
    private DatePicker datePicker;

    public void initialize() {
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
