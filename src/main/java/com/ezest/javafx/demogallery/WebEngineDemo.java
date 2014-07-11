package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WebEngineDemo extends Application {

	Stage stage;
	Scene scene;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
	}

	private void configureStage(){
		stage.setTitle("Web Engine Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		StackPane root = new StackPane();
		root.autosize();	
	    this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("/styles/template.css");
		
		WebView browser = new WebView();
		browser.getEngine().load("www.google.com");
		root.getChildren().add(browser);
	}

}

/**

*/