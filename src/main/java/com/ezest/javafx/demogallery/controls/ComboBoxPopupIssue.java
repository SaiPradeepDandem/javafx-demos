package com.ezest.javafx.demogallery.controls;

import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ComboBoxPopupIssue extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setItems(FXCollections.observableArrayList(Arrays.asList("One", "Two", "Three")));
		StackPane root = new StackPane();
		root.getChildren().add(
				ScrollPaneBuilder
						.create()
						.fitToHeight(true)
						.fitToWidth(true)
						.content(
								StackPaneBuilder.create().minHeight(600).padding(new Insets(50, 20, 20, 20)).alignment(Pos.TOP_LEFT)
										.children(comboBox).build()).build());

		Scene scene = new Scene(root, Color.LINEN);
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(500);
		stage.setHeight(500);
		stage.setScene(scene);
		stage.show();
	}
}
