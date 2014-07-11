package com.ezest.javafx.demogallery.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Windows8ThemeControls extends Application {

	Stage stage;
	Scene scene;
	FlowPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		Button btn = new Button("Check");
		btn.setTooltip(new Tooltip("Hi Sai"));
		RadioButton rb = new RadioButton("Hello");
		CheckBox cb = new CheckBox("Dude");
		root.getChildren().addAll(btn, rb, cb);
		
	}

	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new FlowPane();
		root.setHgap(10);
		root.setVgap(10);
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/win8.css");
	}

}


