package com.ezest.javafx.demogallery.maths;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.PolylineBuilder;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.javafx.experiments.scenicview.ScenicView;

public class SineWaveDemo extends Application {

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

		final SimpleDoubleProperty i = new SimpleDoubleProperty();
		final ObservableList<PathElement> elements = FXCollections.observableArrayList();
		elements.add(new MoveTo(0, 0));

		final Path p = PathBuilder.create().stroke(Color.RED).fill(Color.YELLOW).strokeWidth(2).build();
		final Path p2 = PathBuilder.create().stroke(Color.GREEN).fill(Color.PINK).strokeWidth(2).build();
		final Polyline pl = PolylineBuilder.create().stroke(Color.BLACK).strokeWidth(2).build();
		
		final Point2D orgin = new Point2D(0, 0);
		p.getElements().add(new MoveTo(orgin.getX(), orgin.getY()));
		p2.getElements().add(new MoveTo(orgin.getX(), orgin.getY()));

		final DoubleProperty amplitude = new SimpleDoubleProperty(90); // wave amplitude
		final DoubleProperty rarity = new SimpleDoubleProperty(2); // point spacing
		final DoubleProperty freq = new SimpleDoubleProperty(0.05); // angular frequency
		final DoubleProperty phase = new SimpleDoubleProperty(0); // phase angle

		final Slider ampSlider = new Slider(0, 200, 90);
		ampSlider.valueProperty().bindBidirectional(amplitude);
		final Slider rarSlider = new Slider(.5, 5, 2);
		rarSlider.valueProperty().bindBidirectional(rarity);
		final Slider freSlider = new Slider(0.01, .3, .05);
		freSlider.valueProperty().bindBidirectional(freq);
		final Slider phSlider = new Slider(0, 360, 0);
		phSlider.valueProperty().bindBidirectional(phase);
		 
		DoubleProperty tm = new SimpleDoubleProperty();
		tm.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number t) {
				double k = t.doubleValue();
				
				p.getElements().add(new LineTo(k * rarity.get() + orgin.getX(), Math.sin(freq.get() * (k + phase.get())) * amplitude.get() + orgin.getY()));
				p2.getElements().add(new LineTo(k * rarity.get() + orgin.getX(), Math.cos(freq.get() * (k + phase.get())) * amplitude.get() + orgin.getY()));
			}
		});
		Timeline t = TimelineBuilder.create().build();
		t.setCycleCount(Timeline.INDEFINITE);
		// t.getKeyFrames().add(new KeyFrame(Duration.valueOf("3000ms"), new KeyValue(tm, 500)));
		
		final DoubleProperty len = new SimpleDoubleProperty(0);
		t.getKeyFrames().add(new KeyFrame(Duration.valueOf("25ms"), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				len.set(len.get()+1);
				double k = len.doubleValue();
				p.getElements().add(new LineTo(k * rarity.get() + orgin.getX(), Math.sin(freq.get() * (k + phase.get())) * amplitude.get() + orgin.getY()));
				p2.getElements().add(new LineTo(k * rarity.get() + orgin.getX(), Math.cos(freq.get() * (k + phase.get())) * amplitude.get() + orgin.getY()));
			}
		}));
		
		VBox layout = VBoxBuilder.create().spacing(10).build();
		StackPane wavepane = new StackPane();
		wavepane.setMaxWidth(500);
		wavepane.getChildren().addAll(p, p2);
		
		layout.getChildren().addAll(ampSlider,rarSlider,freSlider,phSlider,ScrollPaneBuilder.create().maxWidth(500).prefWidth(500).prefHeight(400).content(wavepane).build());
		root.getChildren().add(layout);
		t.play();
		

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
		bp.setBottom(getBottom());
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
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
