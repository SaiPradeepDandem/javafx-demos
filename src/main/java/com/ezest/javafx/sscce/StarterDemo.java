package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StarterDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		HBox hb = HBoxBuilder.create().spacing(15).build();
		for (int i = 0; i < 10; i++) {
			hb.getChildren().add(new Starter(new Image(StarterDemo.class.getResourceAsStream("/images/Koala.jpg")), 100,100));
		}
		root.getChildren().add(hb);
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(500);
		stage.setHeight(500);
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
	}

}
