package com.ezest.javafx.demogallery.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MaskAnimationDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	static StackPane mask = StackPaneBuilder.create().style("-fx-cursor:wait;").visible(false).build();
	static Timeline maskTimeLine;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	TextField btn = new TextField("hel");
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		// Logic starts
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER_LEFT);
		hb.setSpacing(20);
		hb.setPadding(new Insets(20));
		
		hb.getChildren().addAll(getButton("Button 1"),getButton("Button 2"),getButton("Button 3"),getButton("Button 4"));
		
		root.getChildren().addAll(hb,mask);
	}

	private Button getButton(final String s){
		Button btn1 = new Button(s);
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				animateMask();
				System.out.println(s+" clicked");
			}
		});
		return btn1;
	}
	public static void animateMask(){
		mask.setVisible(true);
		maskTimeLine = new Timeline(
				new KeyFrame(Duration.millis(5000), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						mask.setVisible(false);
					}
				})
			);
		maskTimeLine.play();
	}
	
	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(400);
	    stage.setHeight(200);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
	}

}

