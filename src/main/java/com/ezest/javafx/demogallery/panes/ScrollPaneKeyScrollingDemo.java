package com.ezest.javafx.demogallery.panes;

import java.lang.reflect.Field;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;

public class ScrollPaneKeyScrollingDemo extends Application {

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

		final ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		sp.setContent(StackPaneBuilder.create().minHeight(1020).prefWidth(200).build());

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ScrollPaneSkin skin = (ScrollPaneSkin) sp.getSkin();
				Field reqField;
				try {
					reqField = ScrollPaneSkin.class.getDeclaredField("hsb");
					reqField.setAccessible(true);
					final ScrollBar scrollBar = (ScrollBar) reqField.get(skin);
					sp.requestFocus();
					sp.setOnKeyPressed(new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent e) {
							if (e.getCode() == KeyCode.DOWN) {
								scrollBar.increment();
								double d = scrollBar.getValue();
								sp.setVvalue(d);
							} else if (e.getCode() == KeyCode.UP) {
								scrollBar.decrement();
								double d = scrollBar.getValue();
								sp.setVvalue(d);
							}
						}
					});
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
		root.getChildren().add(sp);
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(500);
		stage.setHeight(500);
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
