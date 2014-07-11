package com.ezest.javafx.demogallery.text;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MeasureText extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		final Text text = new Text(
				"The is for check of the long text bounds for calculating.The is for check of the long text bounds for calculating.");
		text.setWrappingWidth(150);
		text.snapshot(null, null);
		final double height = text.getLayoutBounds().getHeight();

		VBox vb = new VBox();
		vb.setSpacing(15);
		vb.getChildren().addAll(text, new Label(Double.toString(height)));
		stage.setScene(new Scene(vb));
		stage.show();
	}
}
