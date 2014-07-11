package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class CssStylesDemo extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Group root  = new Group();
		root.getStyleClass().add("mainStage");
		Scene scene = new Scene(root, 1100, 600, Color.BISQUE);
		scene.getStylesheets().add("styles/cssstylesdemo.css");
		
		
	    TilePane tp = new TilePane();
		tp.setPrefColumns(5);
		tp.setVgap(30);
		tp.setHgap(15);
		tp.setPadding(new Insets(10));
		
		
		tp.getChildren().add(getStackPane("custom-node1")); 
		tp.getChildren().add(getStackPane("custom-node2")); 
		tp.getChildren().add(getStackPane("custom-node3")); 
		tp.getChildren().add(getStackPane("custom-node4")); 
		tp.getChildren().add(getStackPane("custom-node5")); 
		tp.getChildren().add(getStackPane("custom-node6")); 
		tp.getChildren().add(getStackPane("custom-node7")); 
		tp.getChildren().add(getStackPane("custom-node8")); 
		tp.getChildren().add(getStackPane("custom-node9")); 
		
	    root.getChildren().addAll(tp);
	    stage.setTitle("JavaFx Styles Demo");
		stage.setScene(scene);
		stage.show();
	}
	
	public StackPane getStackPane(String css){
		StackPane cssNode = new StackPane();
		cssNode.getStyleClass().add(css);
		cssNode.setPrefSize(150, 150);
		return cssNode;
	}

}

