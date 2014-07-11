package com.ezest.javafx.demogallery.controls;

import javax.swing.text.html.MinimalHTMLWriter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class CustomTextAreaDemo extends Application{
	
	Stage stage;
	Scene scene;
	VBox root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
			
		CustomTextArea txt = new CustomTextArea();
		root.getChildren().add(txt);
		
		
	}

	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	    ScenicView.show(scene);
	}
	
	private void configureScene(){
		root = new VBox();
		root.setMaxWidth(100);
		root.setStyle("-fx-border-width:1px;-fx-border-color:red;");
		this.scene = new Scene(StackPaneBuilder.create().padding(new Insets(200)).children(root).build(), Color.LEMONCHIFFON);
		scene.getStylesheets().add("styles/customTextArea.css");
	}

}

class CustomTextArea extends TextArea{
	boolean init = true;
	public CustomTextArea(){
		super();
		
		setPrefHeight(80);
		getStyleClass().add("custom-text-area");
		
		setWrapText(true);
	}
	@Override
	protected void layoutChildren() {
		System.out.println("Init : "+init);
		super.layoutChildren();
		if(init){
			System.out.println("Init : entered");
			Region contentView = (Region)this.lookup(".content");
			super.minHeightProperty().bind(contentView.heightProperty());
			init = false;
		}
		
		//super.minHeightProperty().bind(contentView.heightProperty());
		//super.setMinHeight(contentView.getHeight());
 		
	}
}