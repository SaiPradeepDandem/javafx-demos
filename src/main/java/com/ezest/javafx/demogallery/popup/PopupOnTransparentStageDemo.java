package com.ezest.javafx.demogallery.popup;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopupOnTransparentStageDemo extends Application {

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

		CustomPopUp p3 = new CustomPopUp();
		p3.setX(40);
		p3.show(stage);

		CustomPopUp p2 = new CustomPopUp();
		p2.setX(400);
		p2.show(stage);

		CustomPopUp p1 = new CustomPopUp();
		p1.setX(800);
		p1.show(stage);
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(200);
		stage.setHeight(200);
		stage.setX(0);
		stage.setY(0);
		stage.setScene(this.scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}

	private void configureScene() {
		root = StackPaneBuilder.create().style("-fx-border-width:1px;-fx-border-color:red;").build();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.TRANSPARENT);
	}

	/**
	 * Custom pop up class.
	 */
	class CustomPopUp extends Popup {
		final String style = "-fx-background-color:yellow;-fx-border-width:1px;-fx-border-color:black;-fx-background-radius:5px;-fx-border-radius:5px;";

		public CustomPopUp() {
			super();
			getContent().add(
					StackPaneBuilder.create().style(style).minHeight(200).minWidth(200).alignment(Pos.TOP_RIGHT)
							.children(ButtonBuilder.create().text("Close").onAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent paramT) {
									CustomPopUp.this.hide();
								}
							}).build(), TextFieldBuilder.create().translateY(20).build()).build());
		}
	}
}
