package com.ezest.javafx.effectsdemo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.Separator;
import javafx.scene.effect.DisplacementMapBuilder;
import javafx.scene.effect.FloatMap;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradientBuilder;
import javafx.scene.paint.StopBuilder;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class DisplacementMapBannerDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;

	int W = 320;
	int H = 70;
	double M = 10;
	int BANNER_NB = 8;
	boolean bTestWrap = false;

	FloatMap[] maps = new FloatMap[BANNER_NB];

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		// Logic starts
		for (int i = 0; i < BANNER_NB; i++) {
			maps[i] = new FloatMap(W, H);
		}

		for (int x = 0; x < W/2; x++) {
			double angle = Math.PI * x / 50.0;
			float v = (float) (Math.sin(angle) / 20.0);
			float scaledX = x / W;
			System.out.println(x + " : " + angle + " : " + v);
			for (int y = 0; y < H; y++) {
				float scaledY = y / H;
				float scaledV = (float) (v - (1.0 - scaledY) / 20.0);
				maps[0].setSamples(x, y, 0, 0);
				maps[1].setSamples(x, y, v);
				maps[2].setSamples(x, y, 0, scaledV);
				maps[3].setSamples(x, y, v, scaledV);
				double sq = Math.sqrt(x * x + y * y);
				maps[4].setSamples(x, y, (float) ((115 - sq) / 115.0));
				maps[5].setSamples(x, y, 0, (float) (scaledX * scaledY * 4.7));
				maps[6].setSamples(x, y, 0, (float) (scaledX * 0.5));
				maps[7].setSamples(x, y, (float) (scaledY * 0.5));
			}
		}

		String[] messages = { "Reference", "Accordion", "Wavy Text", "Dizzy Text", "OxoXoxO", "XoxOxoX", "13463463", "2346346436" };
		VBox vb = new VBox();
		vb.setSpacing(15);
		for (int k = 0; k < messages.length; k++) {
			vb.getChildren().add(
					GroupBuilder.create().effect(DisplacementMapBuilder.create().mapData(maps[k]).wrap(bTestWrap).build()).cache(true)
							.children(new Banner(messages[k])).build());
		}
		root.getChildren().add(
				ScrollPaneBuilder.create().fitToHeight(true).fitToWidth(true).style("-fx-background-color:transparent;").content(vb)
						.build());
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(W + 2 * M);
		stage.setHeight((BANNER_NB * (H + M)) + 25);
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setBottom(getBottom());
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.web("#00BABE"));
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

	class Banner extends Group {
		public Banner(String message) {
			Rectangle rectangle = RectangleBuilder
					.create()
					.x(0)
					.y(0)
					.width(W)
					.height(H)
					.fill(LinearGradientBuilder
							.create()
							.startX(0.0)
							.startY(0.0)
							.endX(10.0)
							.endY(0.0)
							.proportional(false)
							.cycleMethod(CycleMethod.REFLECT)
							.stops(StopBuilder.create().offset(0.0).color(Color.BLUE).build(),
									StopBuilder.create().offset(1.0).color(Color.LIGHTBLUE).build()).build()).build();
			Text text = TextBuilder.create().x(25).y(H / 16).text(message).fill(Color.YELLOW).font(Font.font(null, FontWeight.BOLD, 36))
					.build();
			getChildren().addAll(rectangle, text);
		}
	}
}
