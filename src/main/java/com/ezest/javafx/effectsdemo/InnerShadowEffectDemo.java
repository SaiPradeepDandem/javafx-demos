package com.ezest.javafx.effectsdemo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class InnerShadowEffectDemo extends Application {

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
		
		Rectangle sp1 = getBox( "-fx-effect: innershadow( gaussian , blue , 10, 0.5 , 4 , 4 );");
		Rectangle sp2 = getBox( "-fx-effect: innershadow( two-pass-box , blue , 10, 0.5 , 4 , 4 );");
		Rectangle sp3 = getBox( "-fx-effect: innershadow( three-pass-box , blue , 10, 0.5 , 4 , 4 );");
		Rectangle sp3A = getBox( "-fx-effect: innershadow( gaussian , blue , 10, 0.5 , 4 , 4 );");
		
		Rectangle sp4 = getBox( "-fx-effect: innershadow( one-pass-box , blue , 5, 0.5 , 4 , 4 );");
		Rectangle sp5 = getBox( "-fx-effect: innershadow( two-pass-box , blue , 5, 0.5 , 4 , 4 );");
		Rectangle sp6 = getBox( "-fx-effect: innershadow( three-pass-box , blue , 5, 0.5 , 4 , 4 );");
		Rectangle sp6A = getBox( "-fx-effect: innershadow( gaussian , blue , 5, 0.5 , 4 , 4 );");
		
		Rectangle sp7 = getBox( "-fx-effect: innershadow( one-pass-box , blue , 10, 0.8 , 4 , 4 );");
		Rectangle sp8 = getBox( "-fx-effect: innershadow( two-pass-box , blue , 10, 0.8 , 4 , 4 );");
		Rectangle sp9 = getBox( "-fx-effect: innershadow( three-pass-box , blue , 10, 0.8 , 4 , 4 );");
		Rectangle sp9A = getBox( "-fx-effect: innershadow( gaussian , blue , 10, 0.8 , 4 , 4 );");
		
		Rectangle sp10 = getBox( "-fx-effect: innershadow( one-pass-box , blue , 10, 0.2 , 4 , 4 );");
		Rectangle sp11 = getBox( "-fx-effect: innershadow( two-pass-box , blue , 10, 0.2 , 4 , 4 );");
		Rectangle sp12 = getBox( "-fx-effect: innershadow( gaussian , blue , 50, 0.1 , 0 , 0 );");
		Rectangle sp12A = getBox( "-fx-effect: innershadow( gaussian , blue , 10, 0.2 , 4 , 4 );");
		
		Rectangle sp13 = getBox( "-fx-effect: innershadow( one-pass-box , blue , 10, 0.5 , 8 , 16 );");
		Rectangle sp14 = getBox( "-fx-effect: innershadow( two-pass-box , blue , 10, 0.5 , 8 , 16 );");
		Rectangle sp15 = getBox( "-fx-effect: innershadow( three-pass-box , blue , 10, 0.5 , 8 , 16 );");
		Rectangle sp15A = getBox( "-fx-effect: innershadow( gaussian , blue , 10, 0.5 , 8 , 16 );");
		
		root.getChildren().addAll(sp1,sp2,sp3, 
				sp4,sp5,sp6,
				sp7,sp8,sp9, 
				sp10,sp11,sp12, 
				sp13,sp14,sp15
                );
	}

	private Rectangle getBox(String effectStr){
		/*Text sp3 = TextBuilder.create()
				.text("Inner Shadow")
				.fill(Color.YELLOW)
				.font(Font.font(null, FontWeight.BOLD, 40))
				.style(effectStr).build();*/
		Rectangle sp3 = RectangleBuilder.create()
				.width(200)
				.height(200)
				.fill(Color.YELLOW)
				.arcWidth(25)
				.arcHeight(25)
				.style(effectStr).build();
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

