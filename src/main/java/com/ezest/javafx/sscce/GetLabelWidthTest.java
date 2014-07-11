package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GetLabelWidthTest extends Application {
	VBox vb;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Label width test");

		vb = new VBox();
		vb.setAlignment(Pos.TOP_CENTER);
		
		Button addLabelButton = new Button();
		addLabelButton.setText("add label");
		addLabelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Label label = new Label();
				label.setText("test labeltest labeltest labeltest labeltest labeltest labeltest labeltest labeltest label");
				vb.getChildren().add(label);
			}
		});
		
		// Wrapping the button in a stackpane to align to left.
		StackPane sp = new StackPane();
		sp.setAlignment(Pos.CENTER_LEFT);
		sp.getChildren().add(addLabelButton);
		
		vb.getChildren().add(sp);

		Scene scene = new Scene(vb, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
