package com.ezest.javafx.components.horizontaldragger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * 
 * @author amol.hingmire
 * 
 */
public class MainApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		Button buttonToDrag1 = ButtonBuilder.create().text("Draggable Button 1").id("b1").build();
		Button buttonToDrag2 = ButtonBuilder.create().text("Draggable Button 2").id("b2").build();
		Button buttonToDrag3 = ButtonBuilder.create().text("Draggable Button 3").id("b3").build();

		DragUtility dragUtility = new DragUtility();

		dragUtility.setDragControl(buttonToDrag1);
		dragUtility.setDragControl(buttonToDrag2);
		// dragUtility.setDragControl(buttonToDrag3);

		HorizontalDock horizontalDock = new HorizontalDock();
		horizontalDock.addChildren(buttonToDrag1);
		horizontalDock.addChildren(buttonToDrag2);
		// horizontalDock.addChildren(buttonToDrag3);

		dragUtility.setTargetPane(horizontalDock);

		stage.centerOnScreen();

		Scene scene = new Scene(horizontalDock, 750, 500, Color.AQUAMARINE);
		stage.setScene(scene);

		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
