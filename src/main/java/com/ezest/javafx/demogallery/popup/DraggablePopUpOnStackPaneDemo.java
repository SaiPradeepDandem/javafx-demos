package com.ezest.javafx.demogallery.popup;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DraggablePopUpOnStackPaneDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	private EventHandler<MouseEvent> mousePressEvent;
	private EventHandler<MouseEvent> mouseDraggedEvent;
	private double startDragX;
	private double startDragY;
	private double startNodeX;
	private double startNodeY;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();

		StackPane sp = StackPaneBuilder
				.create()
				.maxHeight(40)
				.maxWidth(100)
				.style("-fx-background-color:red;-fx-border-width:1px;-fx-border-color:black;-fx-background-radius:5px;-fx-border-radius:5px;-fx-cursor:hand;")
				.children(new Label("Add Note")).build();
		sp.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				CustomPopUp p = new CustomPopUp(root);
				p.setTranslateX(600);
				p.setTranslateY(200);
				initEventHandlers(p);
				root.getChildren().add(p);
			}
		});
		root.getChildren().add(sp);
		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				System.out.println(arg2);
			}
		});
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
		stage.setY(0);
		stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
		stage.setScene(this.scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}

	private void configureScene() {
		root = StackPaneBuilder.create().style("-fx-border-width:1px;-fx-border-color:red;").alignment(Pos.TOP_LEFT).build();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.TRANSPARENT);
	}

	/**
	 * Sets the mouse events on the provided component.
	 * 
	 * @param comp
	 *            CustomPopUp on which the mouse listeners need to be set.
	 */
	private void initEventHandlers(final CustomPopUp popUp) {
		mousePressEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				popUp.toFront();
				// Registering the co-ordinates.
				startDragX = me.getSceneX();
				startDragY = me.getSceneY();
				startNodeX = popUp.getTranslateX();
				startNodeY = popUp.getTranslateY();
			}
		};

		mouseDraggedEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				double xTr = startNodeX + (me.getSceneX() - startDragX);
				double yTr = startNodeY + (me.getSceneY() - startDragY);
				double mxDiff = root.getWidth() - popUp.getWidth();
				double myDiff = root.getHeight() - popUp.getHeight();
				xTr = xTr < 0 ? 0 : (xTr > mxDiff ? mxDiff : xTr);
				yTr = yTr < 0 ? 0 : (yTr > myDiff ? myDiff : yTr);

				popUp.setTranslateX(xTr < 0 ? 0 : xTr);
				popUp.setTranslateY(yTr);
			}
		};

		popUp.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEvent);
		popUp.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressEvent);
	}

	/**
	 * Custom pop up class.
	 */
	class CustomPopUp extends StackPane {
		final StackPane parent;
		final String style = "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 8, 0.0 , 0 , 2 );"
				+ "-fx-background-color:yellow;-fx-border-width:1px;-fx-border-color:black;"
				+ "-fx-background-radius:5px;-fx-border-radius:5px;";

		public CustomPopUp(StackPane parentWindow) {
			super();
			this.parent = parentWindow;
			setMaxHeight(200);
			setMaxWidth(200);
			getChildren().add(
					StackPaneBuilder.create().style(style).minHeight(200).minWidth(200).alignment(Pos.TOP_RIGHT)
							.children(ButtonBuilder.create().text("Close").onAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent paramT) {
									parent.getChildren().remove(CustomPopUp.this);
								}
							}).build(), TextFieldBuilder.create().translateY(20).build()).build());
		}
	}
}
