package com.ezest.javafx.demogallery.controls;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleButtonBuilder;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class ToggleButtonGraphicStylingDemo extends Application {

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
		ToggleGroup tg = new ToggleGroup();
		final ToggleButton tb1 = ToggleButtonBuilder.create().styleClass("graphic-toggle-button").toggleGroup(tg).graphic(buildDiseaseStatusIcon(true)).text("Not present in family").build();
		tb1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(tb1.selectedProperty().get()){
					System.out.println("Selected");
				}else{
					System.out.println("Deselected");
				}
			}
		});
		ToggleButton tb2 = ToggleButtonBuilder.create().styleClass("graphic-toggle-button").toggleGroup(tg).graphic(buildDiseaseStatusIcon(false)).text("Not present in family").build();
		
		// Logic starts
		HBox hb = HBoxBuilder.create().spacing(15).build();
		hb.getChildren().addAll(tb1,tb2);
		root.getChildren().add(hb);
		//ScenicView.show(scene);
	}

	private Node buildDiseaseStatusIcon(boolean item) {
		// TODO : Increased verbosity of the code. Here only the color code is changed. you can set the color code in if condition and build
		// a circle with the color code variable.
		Circle circle = null;
		if (item) {
			circle = CircleBuilder.create().radius(5).fill(Color.web("#D9D900")).styleClass("yellowEffect").stroke(Color.web("#D9D900")).build();
		} else {
			circle = CircleBuilder.create().radius(5).fill(Color.web("#5FD095")).styleClass("redEffect").stroke(Color.web("#5FD095")).build();
		}
		return circle;
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
		BorderPane bp = new BorderPane();
		bp.setBottom(getBottom());
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.WHITE);
		scene.getStylesheets().add("styles/ToggleButtonGraphicStylingDemo.css");
	}

	private Node getBottom() {
		StackPane sp = new StackPane();
		sp.setMinHeight(25);
		sp.setAlignment(Pos.TOP_RIGHT);
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/mglass.gif")));
		image.setCursor(Cursor.HAND);
		image.setTranslateX(-5);
		image.setTranslateY(3);
		image.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				ScenicView.show(scene);
			}
		});
		sp.getChildren().addAll(new Separator(),image);
		return sp;
	}

}

