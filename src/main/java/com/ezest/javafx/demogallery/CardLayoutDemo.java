package com.ezest.javafx.demogallery;

import java.awt.CardLayout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class CardLayoutDemo extends Application {

	Stage stage;
	Scene scene;
	Group root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configureCardLayout();
	}

	private void configureCardLayout() {
		// TODO Auto-generated method stub
		
	}

	private void configureStage(){
		stage.setTitle("Card Layout Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new Group();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

}


