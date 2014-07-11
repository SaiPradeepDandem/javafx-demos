package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;

import com.ezest.javafx.common.FXTools;

public class ScrollInPopUpDemo extends Application {

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
		
		final Button btn = new Button("Show Pop Up");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Popup popup = new Popup();
				popup.setAutoHide(true);
				popup.setAutoFix(true);
				popup.setHideOnEscape(true);
				
				VBox listView = new VBox();
				for (int i = 0; i < 20; i++) {
					listView.getChildren().add(StackPaneBuilder.create().prefHeight(40).children(new Label("Hello")).build());
				}
				
				ScrollPane scrollPane = new ScrollPane();
				scrollPane.setPrefHeight(200);
				scrollPane.setPrefWidth(200);
				scrollPane.setContent(listView);
				
				popup.getContent().add(scrollPane);
				Point2D d = FXTools.getAbsoluteStartPointOfNode(btn);
				popup.show(btn, d.getX(),  d.getY());
			}
		});
		root.getChildren().add(btn);
	}

		private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(700);
	    stage.setHeight(600);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		root.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(90,0,0,0));
		this.scene = new Scene(root, Color.LINEN);
	}

}


