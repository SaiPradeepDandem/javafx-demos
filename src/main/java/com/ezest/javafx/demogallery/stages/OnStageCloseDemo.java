/**
 * 
 */
package com.ezest.javafx.demogallery.stages;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Demo class to check the onCloseRequest method functionality of the Stage class.
 * 
 * @author Sai.Dandem
 * 
 */
public class OnStageCloseDemo extends Application {

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
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.out.println("Closing......");
				event.consume();
			}
		});
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
		stage.setY(0);
		stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
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
