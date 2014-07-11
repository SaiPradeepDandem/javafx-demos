package com.ezest.javafx.components.freetextfield;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class DynamicTextAreaDemo extends Application {

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
		VBox vb = new VBox();
		vb.setSpacing(10);
		
		final VBox layout = VBoxBuilder.create().build();
		layout.getChildren().add(new DynamicTextArea());
		
		Button btn = ButtonBuilder.create().text("Add").onAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				layout.getChildren().add(new DynamicTextArea());
			}
		}).build();

		final GridPane gridPane = GridPaneBuilder.create()
				.styleClass("contact-details-gridpane")
				// [ARE] Further modification for CAEMR-2098. Setting minimum width to show labels even if application width is changed.
				.columnConstraints(ColumnConstraintsBuilder.create().hgrow(Priority.NEVER).minWidth(80).build(),
						ColumnConstraintsBuilder.create().hgrow(Priority.ALWAYS).build(),
						ColumnConstraintsBuilder.create().hgrow(Priority.NEVER).minWidth(100).build()).build();
		
		gridPane.addRow(0, new Label("hi"), layout, btn);
		
		root.getChildren().add(ScrollPaneBuilder.create().styleClass("contact-details-pane").hbarPolicy(ScrollBarPolicy.NEVER)
				.fitToWidth(true).content(gridPane).build());
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(700);
		stage.setHeight(600);
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
		scene.getStylesheets().add("styles/demoTemplate.css");
	}

	private Node getBottom() {
		StackPane sp = new StackPane();
		sp.setMinHeight(25);
		sp.setAlignment(Pos.TOP_RIGHT);
		Circle c = CircleBuilder.create().fill(Color.RED).translateX(-5).translateY(3).radius(8).cursor(Cursor.HAND).build();
		c.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				ScenicView.show(scene);
			}
		});
		sp.getChildren().addAll(new Separator(), c);
		return sp;
	}

}

