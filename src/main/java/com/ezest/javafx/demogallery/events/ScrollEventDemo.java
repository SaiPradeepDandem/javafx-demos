package com.ezest.javafx.demogallery.events;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ScrollEventDemo extends Application {

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
		// Logic starts
		StackPane sp = new StackPane();
		sp.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent e) {
				System.out.println("*********************************************************************");
				System.out.println("Delta X       : " + e.getDeltaX());
				System.out.println("Delta Y       : " + e.getDeltaY());
				System.out.println("Text Delta X  : " + e.getTextDeltaX());
				System.out.println("Text Delta Y  : " + e.getTextDeltaY());
				System.out.println("Text Delta X Units: " + e.getTextDeltaXUnits());
				System.out.println("Text Delta Y Units: " + e.getTextDeltaYUnits());
				System.out.println("Total Delta X : " + e.getTotalDeltaX());
				System.out.println("Total Delta Y : " + e.getTotalDeltaY());
				System.out.println("Touch Count   : " + e.getTouchCount());

				if (e.isControlDown()) {
					System.out.println("-------------- Control Key is pressed and scrolled-------------");
				} else if (e.isAltDown()) {
					System.out.println("-------------- Alt Key is pressed and scrolled-------------");
				} else if (e.isShiftDown()) {
					System.out.println("-------------- Shift Key is pressed and scrolled-------------");
				}
			}
		});
		root.getChildren().add(sp);
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(300);
		stage.setHeight(300);
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
	}

}
