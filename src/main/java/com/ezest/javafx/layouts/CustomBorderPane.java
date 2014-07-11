package com.ezest.javafx.layouts;

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CustomBorderPane extends Application {

	StackPane root;
	StackPane leftPane;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new StackPane();
		Scene scene = new Scene(root, 1124, 650);
		root.getChildren().add( getBorderPane() );
		stage.setTitle("JavaFx Layout Demo");
		stage.setScene(scene);
		stage.show();
	}

	private Node getBorderPane() {
		BorderPane layout = new BorderPane();
		layout.setTop( getTopPane());
		layout.setBottom(getBottomPane());
		layout.setCenter(getCenterPane());
		layout.setLeft(getLeftPane());
		layout.setRight(getRightPane());
		return layout;
	}

	private Node getTopPane() {
		StackPane sp = new StackPane();
		sp.setStyle("-fx-background-color:#626262,#F0F0F0;-fx-background-insets:0, 25 0 0 0;");
		sp.setPrefHeight(100);
		return sp;
	}
	private Node getBottomPane() {
		StackPane sp = new StackPane();
		sp.setStyle("-fx-background-color:#626262;");
		sp.setPrefHeight(25);
		return sp;
	}
	private Node getCenterPane() {
		StackPane sp = new StackPane();
		sp.setStyle("-fx-background-color:#F0F0F0;");
		sp.setPadding(new Insets(0,10,10,10));
		sp.getChildren().add( getBox() );
		return sp;
	}
	
	private Node getRightPane() {
		StackPane rightPane = new StackPane();
		rightPane.setStyle("-fx-background-color:#F0F0F0;");
		rightPane.setPadding(new Insets(0,10,10,10));
		rightPane.setPrefWidth(300);
		rightPane.getChildren().add( getBox() );
		
		final double translateVal = 55.0D;
		rightPane.setTranslateY(-1 * translateVal);
		rightPane.minHeightProperty().bind(new DoubleBinding() {
			{
				bind(leftPane.heightProperty());
			}
			@Override
			protected double computeValue() {
				return (leftPane.getHeight()+translateVal);
			}
		});
		return rightPane;
	}
	
	private Node getLeftPane() {
		leftPane = new StackPane();
		leftPane.setStyle("-fx-background-color:#F0F0F0;");
		leftPane.setPadding(new Insets(0,10,10,10));
		leftPane.setPrefWidth(200);
		leftPane.getChildren().add(getBox());
		return leftPane;
	}
	
	private Node getBox(){
		StackPane sp = new StackPane();
		sp.setStyle("-fx-background-color:#000000,#FFFFFF;-fx-background-insets:0, 1;");
		return sp;
	}

}
