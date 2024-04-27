package com.mermil.erp;

import com.mermil.erp.services.StageNavigationServices;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ErpApplication extends Application {

	public static Parent rootNode;
	private static EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeEntityManagerFactory();
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
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}

	}

	private void initializeEntityManagerFactory() {
		entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

}
