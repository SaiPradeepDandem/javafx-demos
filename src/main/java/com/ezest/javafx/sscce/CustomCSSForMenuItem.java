package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * Incomplete
 * @author Sai.Dandem
 *
 */
public class CustomCSSForMenuItem extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		MenuButton btnList = new MenuButton();
		MenuItem item = new MenuItem("Testing");
		item.getStyleClass().add("myMenuItem");
		btnList.getItems().add(item);

		root.getChildren().add(btnList);
		Scene scene = new Scene(root, 500, 500, Color.LINEN);
		stage.setWidth(500);
	    stage.setHeight(500);
	    stage.setTitle("JavaFx Custom CSS For MenuItem Demo");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
