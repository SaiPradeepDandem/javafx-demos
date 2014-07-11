/**
 * 
 */
package com.ezest.javafx.effectsdemo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.Separator;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.FloatMap;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

/**
 * Demo class to show the effect of mask on the top and bottom edges of the scroll pane.
 * 
 * @author Sai.Dandem
 * 
 */
public class ScrollPaneDisplacementDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;

	public static void main(String[] args) {
		Application.launch(args);
	}

	double width = 200;
	double height = 200;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 150; i++) {
			sb.append("test ");
		}
		// Logic starts
		VBox vb = new VBox();
		vb.setStyle("-fx-border-width:1px;-fx-border-color:red;");
		vb.setPadding(new Insets(30));
		Text lbl = TextBuilder.create().text(sb.toString()).wrappingWidth(150).build();
		ScrollPane scroll = ScrollPaneBuilder.create().maxWidth(width).maxHeight(height).minWidth(width).minHeight(height)
				.content(StackPaneBuilder.create().alignment(Pos.TOP_LEFT).minHeight(400).children(lbl).build()).build();
		// vb.getChildren().addAll(scroll);
		root.getChildren().add(
				GroupBuilder.create()
						.children(StackPaneBuilder.create().style("-fx-border-width:1px;-fx-border-color:red;").children(scroll).build())
						.build());
		// applySineWaveEffect(scroll);
		// applyCosWaveEffect(scroll);
		applyEffect(scroll);

	}

	private void applyEffect(ScrollPane scroll) {
		DisplacementMap displacementMap = new DisplacementMap();
		FloatMap floatMap = new FloatMap((int) width, (int) height);
		for (int i = 0; i < (int) width; i++) {
			double v = (Math.sin(i / 50.0 * Math.PI) - 1) / 10.0;
			for (int j = 0; j < (int) height; j++) {
				floatMap.setSamples(i, j, 0, (float) v);
			}
		}
		displacementMap.setMapData(floatMap);
		scroll.setEffect(displacementMap);
	}

	private void applyCosWaveEffect(ScrollPane scroll) {
		DisplacementMap displacementMap = new DisplacementMap();
		FloatMap floatMap = new FloatMap((int) width, (int) height);
		for (int i = 0; i < (int) width; i++) {
			double v = (Math.cos(i / 20.0 * Math.PI) - 0.5) / 40.0;
			for (int j = 0; j < (int) height; j++) {
				floatMap.setSamples(i, j, 0, (float) v);
			}
		}
		displacementMap.setMapData(floatMap);
		scroll.setEffect(displacementMap);
	}

	private void applySineWaveEffect(ScrollPane scroll) {
		DisplacementMap displacementMap = new DisplacementMap();
		FloatMap floatMap = new FloatMap((int) width, (int) height);
		for (int i = 0; i < (int) width; i++) {
			double v = (Math.sin(i / 20.0 * Math.PI) - 0.5) / 40.0;
			for (int j = 0; j < (int) height; j++) {
				floatMap.setSamples(i, j, 0, (float) v);
			}
		}
		displacementMap.setMapData(floatMap);
		scroll.setEffect(displacementMap);
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(400);
		stage.setHeight(400);
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		System.out.println(10 / 5 * 2);
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
