package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HeightsDemo extends Application {

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
		
		GridPane gp = new GridPane();
		gp.setGridLinesVisible(true);
		
		ScrollPane sp1 = new ScrollPane();
		sp1.setPrefHeight(300);
		sp1.setStyle("-fx-border-color:red;-fx-border-width:1px;-fx-border-style:solid;");
		sp1.setContent(new Label("Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1Scroll Pane1"));
		
		StackPane sp2 = new StackPane();
		sp2.setStyle("-fx-border-color:red;-fx-border-width:1px;-fx-border-style:solid;");
		
		StackPane sp3 = new StackPane();
		sp3.setPrefHeight(200);
		sp3.setStyle("-fx-border-color:green;-fx-border-width:1px;-fx-border-style:solid;-fx-background-color:yellow;");
		sp3.getChildren().add(new Label("hello"));
		
		sp2.getChildren().add(sp3);
		sp2.setPrefHeight(150);
		
		Group gp1 = new Group();
		gp1.getChildren().add(sp2);
		gp.add(sp1, 0, 0);
		gp.add(gp1, 1, 0);
		
		ColumnConstraints column1Constraint = new ColumnConstraints();
		column1Constraint.setPercentWidth(25);

		ColumnConstraints column2Constraint = new ColumnConstraints();
		column2Constraint.setPercentWidth(75);
		gp.getColumnConstraints().addAll(column1Constraint,column2Constraint);

		root.getChildren().add(gp);
	}

	private void configureStage(){
		stage.setTitle("Heights Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(600);
	    stage.setHeight(600);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		root.setStyle("-fx-border-color:green;-fx-border-width:1px;-fx-border-style:solid;-fx-background-color:#C0C0C0;");
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

}


