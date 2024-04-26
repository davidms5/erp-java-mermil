package com.mermil.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mermil.erp.services.StageNavigationServices;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mermil.erp.repository")
public class ErpApplication extends Application {

	private ConfigurableApplicationContext applicationContext;
	public static Parent rootNode;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		applicationContext = SpringApplication.run(ErpApplication.class);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));

		loader.setControllerFactory(applicationContext::getBean);
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
		applicationContext.close();
	}

}
