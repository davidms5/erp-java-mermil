package com.mermil.erp.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

@Service
public class StageNavigationServices {

    private static final Map<String, Stage> popupScreens = new HashMap<>();
    private static int isPopupOpenCounter = 0;

    public static boolean arePopupsOpen() {
        return !popupScreens.isEmpty(); // Return true if there are open popup screens
    }

    public static void ChangeScene(ActionEvent event, String fxmlPath, String title) {

        try {
            FXMLLoader loader = new FXMLLoader(StageNavigationServices.class.getResource(fxmlPath));
            Parent newSceneRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();

            double xPos = (screenWidth - 800) / 2; // Calculate center X position
            double yPos = (screenHeight - 600) / 2; // Calculate center Y position

            stage.setScene(new Scene(newSceneRoot));
            stage.setTitle(title);
            stage.setX(xPos); // Set X position
            stage.setY(yPos);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showPopupScreen(ActionEvent event, String fxmlPath, String title) {
        try {
            if (!popupScreens.containsKey(fxmlPath)) {
                FXMLLoader loader = new FXMLLoader(StageNavigationServices.class.getResource(fxmlPath));
                Parent root = loader.load();
                Stage popupStage = new Stage();
                popupStage.setScene(new Scene(root));
                popupStage.setTitle(title);
                popupStage.getIcons().add(new Image("/logo.jpeg"));
                popupStage.setOnCloseRequest(e -> { // Listen for close events on the popup screen
                    isPopupOpenCounter--; // Update flag when popup screen is closed
                    popupScreens.remove(fxmlPath);
                });

                popupStage.show();
                popupScreens.put(fxmlPath, popupStage);
                isPopupOpenCounter++;

            } else {
                popupScreens.get(fxmlPath).requestFocus(); // Focus the existing popup stage
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setMainScreenCloseListener(Stage mainScreen) {
        mainScreen.setOnCloseRequest(e -> {
            if (isPopupOpenCounter > 0) {
                e.consume(); // Prevent closing the main screen if any popup screen is open
            }
        });
    }
}
