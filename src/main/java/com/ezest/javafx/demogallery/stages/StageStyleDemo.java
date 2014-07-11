package com.ezest.javafx.demogallery.stages;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageStyleDemo extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		StackPane sp = new StackPane();
		sp.setStyle(getStyle());
		Scene sc = new Scene(sp,Color.TRANSPARENT);
		stage.setScene(sc);
		stage.setWidth(600);
		stage.setHeight(600);
		//stage.setOpacity(.1);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}
	
	private String getStyle(){
		return " -fx-border-insets: 23; "+
				" -fx-background-insets: 23;"+
				" -fx-background-radius: 6;"+
				" -fx-background-color: pink;"+
				" -fx-border-radius: 6;"+
				" -fx-border-color: gray;"+
				" -fx-border-style: solid;"+
				" -fx-border-width: 1;"+
				" -fx-effect: dropshadow(three-pass-box, rgba(100, 100, 100, 1), 24, 0.5, 0, 0);"+
				" }";
	}
}
