package com.ezest.javafx.effectsdemo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DropShadowEffectDemo extends Application {

	Stage stage;
	Scene scene;
	TilePane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		StackPane sp1 = getBox( "-fx-effect: dropshadow( one-pass-box , blue , 10, 0.5 , 4 , 4 );");
		StackPane sp2 = getBox( "-fx-effect: dropshadow( two-pass-box , blue , 10, 0.5 , 4 , 4 );");
		StackPane sp3 = getBox( "-fx-effect: dropshadow( three-pass-box , blue , 10, 0.5 , 4 , 4 );");
		StackPane sp3A = getBox( "-fx-effect: dropshadow( gaussian , blue , 10, 0.5 , 4 , 4 );");
		
		StackPane sp4 = getBox( "-fx-effect: dropshadow( one-pass-box , blue , 5, 0.5 , 4 , 4 );");
		StackPane sp5 = getBox( "-fx-effect: dropshadow( two-pass-box , blue , 5, 0.5 , 4 , 4 );");
		StackPane sp6 = getBox( "-fx-effect: dropshadow( three-pass-box , blue , 5, 0.5 , 4 , 4 );");
		StackPane sp6A = getBox( "-fx-effect: dropshadow( gaussian , blue , 5, 0.5 , 4 , 4 );");
		
		StackPane sp7 = getBox( "-fx-effect: dropshadow( one-pass-box , blue , 10, 0.8 , 4 , 4 );");
		StackPane sp8 = getBox( "-fx-effect: dropshadow( two-pass-box , blue , 10, 0.8 , 4 , 4 );");
		StackPane sp9 = getBox( "-fx-effect: dropshadow( three-pass-box , blue , 10, 0.8 , 4 , 4 );");
		StackPane sp9A = getBox( "-fx-effect: dropshadow( gaussian , blue , 10, 0.8 , 4 , 4 );");
		
		StackPane sp10 = getBox( "-fx-effect: dropshadow( one-pass-box , blue , 10, 0.2 , 4 , 4 );");
		StackPane sp11 = getBox( "-fx-effect: dropshadow( two-pass-box , blue , 10, 0.2 , 4 , 4 );");
		StackPane sp12 = getBox( "-fx-effect: dropshadow( three-pass-box , blue , 10, 0.2 , 4 , 4 );");
		StackPane sp12A = getBox( "-fx-effect: dropshadow( gaussian , blue , 10, 0.2 , 4 , 4 );");
		
		StackPane sp13 = getBox( "-fx-effect: dropshadow( one-pass-box , blue , 10, 0.5 , 8 , 16 );");
		StackPane sp14 = getBox( "-fx-effect: dropshadow( two-pass-box , blue , 10, 0.5 , 8 , 16 );");
		StackPane sp15 = getBox( "-fx-effect: dropshadow( three-pass-box , blue , 10, 0.5 , -4 , -4 );");
		StackPane sp15A = getBox( "-fx-effect: dropshadow( gaussian , blue , 15, 0.5 , 0 , 0 );");
		
		final Button btn = new Button("show me");
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent paramT) {
				btn.setStyle("-fx-effect: dropshadow( gaussian , red , 15, 0.5 , 0 , 0 );");
			}
		});
		btn.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent paramT) {
				btn.setStyle("-fx-effect: null;");
			}
		});
		
		root.getChildren().addAll(sp1,sp2,sp3, sp3A,
				sp4,sp5,sp6,
				sp7,sp8,sp9, 
				sp10,sp11,sp12, 
				sp13,sp14,sp15A,btn
                );
	}

	private StackPane getBox(String effectStr){
		StackPane sp3 = StackPaneBuilder.create()
				.minHeight(200)
				.minWidth(200)
				.style("-fx-border-width:1px;"+
				       "-fx-border-color:red;"+effectStr).build();
		return sp3;
       
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
		root = new TilePane();
		root.setPrefColumns(4);
		root.setHgap(10);
		root.setVgap(10);
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

}

