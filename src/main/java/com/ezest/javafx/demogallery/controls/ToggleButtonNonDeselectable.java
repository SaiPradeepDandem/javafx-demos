package com.ezest.javafx.demogallery.controls;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;
import com.sun.javafx.scene.control.behavior.TextInputControlBehavior;
import com.sun.javafx.scene.control.skin.TextInputControlSkin;

public class ToggleButtonNonDeselectable extends Application {

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

		NonDeselectableToggleButton tb1 = new NonDeselectableToggleButton(
				"Hello World");
		tb1.getToggleButton().setPrefWidth(200);
		NonDeselectableToggleButton tb2 = new NonDeselectableToggleButton(
				"Super World");
		ToggleGroup gp = new ToggleGroup();
		tb1.setToggleGroup(gp);
		tb2.setToggleGroup(gp);
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.getChildren().addAll(tb1, tb2);
		root.getChildren().add(vb);

	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
		stage.setY(0);
		stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setBottom(getBottom());
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
	}

	private Node getBottom() {
		StackPane sp = new StackPane();
		sp.setMinHeight(25);
		sp.setAlignment(Pos.TOP_RIGHT);
		ImageView image = new ImageView(new Image(getClass()
				.getResourceAsStream("/images/mglass.gif")));
		image.setCursor(Cursor.HAND);
		image.setTranslateX(-5);
		image.setTranslateY(3);
		image.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				ScenicView.show(scene);
			}
		});
		sp.getChildren().addAll(new Separator(), image);
		return sp;
	}

	class NonDeselectableToggleButton extends StackPane {
		private ToggleButton toggleButton;
		private final StackPane mask = new StackPane();

		public NonDeselectableToggleButton() {
			super();
			this.toggleButton = new ToggleButton();
			configure();
		}

		public NonDeselectableToggleButton(String text) {
			super();
			this.toggleButton = new ToggleButton(text);
			configure();
		}

		private void configure() {
			mask.setVisible(false);
			this.setAlignment(Pos.TOP_LEFT);
			this.getChildren().addAll(toggleButton, mask);
			
			toggleButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("action");
					//toggleButton.setSelected(true);
				}
			});
			toggleButton.selectedProperty().addListener(
					new ChangeListener<Boolean>() {
						@Override
						public void changed(
								ObservableValue<? extends Boolean> arg0,
								Boolean arg1, Boolean selected) {
							System.out.println(selected);
							if (selected) {
								mask.setVisible(true);
								// NonDeselectableToggleButton.this.setDisable(true);
							} else {
								mask.setVisible(false);
								// NonDeselectableToggleButton.this.setDisable(false);
							}
						}
					});

		}

		public ToggleButton getToggleButton() {
			return toggleButton;
		}

		public void setToggleGroup(ToggleGroup group) {
			this.toggleButton.setToggleGroup(group);
		}
	}
	
	interface A{
		
	}
	enum B implements A{
		
	}
}
