package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ScrollPaneDemo extends Application {

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
		
		Rectangle rect1 = new Rectangle(100, 100, Color.ORANGE);
		Rectangle rect2 = new Rectangle(100, 100, Color.WHITE);
		Rectangle rect3 = new Rectangle(100, 100, Color.GREEN);
		HBox hb = new HBox();
		hb.getChildren().addAll(rect1,rect2,rect3);
		
		Rectangle rect4 = new Rectangle(100, 100, Color.BLUE);
		Rectangle rect5 = new Rectangle(100, 100, Color.GRAY);
		Rectangle rect6 = new Rectangle(100, 100, Color.RED);
		HBox hb1 = new HBox();
		hb1.getChildren().addAll(rect4,rect5,rect6);
		
		VBox vb = new VBox();
		vb.getChildren().addAll(hb,hb1);
		
		 ScrollPane s1 = new ScrollPane();
		 s1.setPrefSize(150, 150);
		 s1.setContent(vb);
		 s1.setVmax(.85);
		 s1.setVmin(0);
		 root.getChildren().add(s1);
	}

	private void configureStage(){
		stage.setTitle("Scroll Pane Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(500);
	    stage.setHeight(600);
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


