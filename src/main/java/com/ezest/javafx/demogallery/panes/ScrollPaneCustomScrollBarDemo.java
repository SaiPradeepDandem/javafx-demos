package com.ezest.javafx.demogallery.panes;

import java.util.HashMap;

import com.javafx.experiments.scenicview.ScenicView;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScrollPaneCustomScrollBarDemo extends Application {

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
		
		ScrollPane bodyScroll = ScrollPaneBuilder.create().fitToHeight(true)
                .fitToWidth(true)
                .minHeight(40)
                .build();
		
		VBox testvb = new VBox();
		for (int i = 0; i < 30; i++) {
		testvb.getChildren().add(new Label("hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello "));
		}
		bodyScroll.setContent(testvb);
		StackPane sp = new StackPane();
		sp.setMaxHeight(200);
		sp.getChildren().add(bodyScroll);
		
		StackPane sp1 = StackPaneBuilder.create().translateY(20).style("-fx-background-color:#86032B;").opacity(.59).maxHeight(200).maxWidth(200).build();
		root.getChildren().addAll(sp,sp1);
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
		root = new StackPane();
		root.autosize();
		root.maxHeight(200);
		this.scene = new Scene(root, Color.WHITE);
		scene.getStylesheets().add("styles/customScrollBar.css");
		ScenicView.show(scene);
	}

}


