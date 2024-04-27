package com.mermil.erp;



import com.mermil.erp.services.StageNavigationServices;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ErpApplication extends Application {

	public static Parent rootNode;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));

		rootNode = loader.load();

		primaryStage.setScene(new Scene(rootNode));
		primaryStage.setTitle("login");
		primaryStage.getIcons().add(new Image("/logo.jpeg"));

		primaryStage.show();
		// Call setMainScreenCloseListener to prevent closing the main screen while
		// popups are open
		StageNavigationServices.setMainScreenCloseListener(primaryStage);

	}

	@Override
	public void stop() throws Exception {
		super.stop();

	}

}
