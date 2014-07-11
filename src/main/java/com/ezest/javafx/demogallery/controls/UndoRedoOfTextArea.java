package com.ezest.javafx.demogallery.controls;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.sun.javafx.scene.control.behavior.TextInputControlBehavior;
import com.sun.javafx.scene.control.skin.TextInputControlSkin;

public class UndoRedoOfTextArea extends Application {

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
		
		final TextArea txt = new TextArea();
		Button btn = new Button("Undo");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				((TextInputControlBehavior)((TextInputControlSkin)txt.getSkin()).getBehavior()).callAction("Undo");
			}
		});
		Button btn2 = new Button("Redo");
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				((TextInputControlBehavior)((TextInputControlSkin)txt.getSkin()).getBehavior()).callAction("Redo");
			}
		});
		VBox vb = new VBox();
		vb.setSpacing(15);
		vb.getChildren().addAll(txt,btn, btn2);
		root.getChildren().add(vb);
	
	}

	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(500);
	    stage.setHeight(500);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

}


