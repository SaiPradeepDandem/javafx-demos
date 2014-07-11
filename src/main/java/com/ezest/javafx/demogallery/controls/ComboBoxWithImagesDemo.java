package com.ezest.javafx.demogallery.controls;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.javafx.experiments.scenicview.ScenicView;

public class ComboBoxWithImagesDemo extends Application {

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
		ObservableList<String> styles = FXCollections.observableArrayList();
		styles.add("-fx-stroke-dash-array: 2 4;-fx-stroke-line-join:round;-fx-stroke-line-cap:round;");
		styles.add("-fx-stroke-dash-array: 2 2;-fx-stroke-line-join:round;-fx-stroke-line-cap:round;");
		styles.add("-fx-stroke-dash-array: 1;-fx-stroke-line-join:round;-fx-stroke-line-cap:round;");
		
		ComboBox<String> lineComboBox = new ComboBox<String>();
		lineComboBox.getItems().addAll(styles);
		Callback<ListView<String>, ListCell<String>> cellFactory = new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> p) {
				return new ListCell<String>() {
					private final StackPane sp;
					private final Line line;
					{
						setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
						sp = new StackPane();
						line = new Line(0, 15, 100, 15);
						sp.getChildren().add(line);
					}

					@Override
					protected void updateItem(String style, boolean empty) {
						super.updateItem(style, empty);
						if (style != null && !empty) {
							line.setStyle(style);
							setGraphic(line);
						}
					}
				};
			}
		};
		lineComboBox.setButtonCell(cellFactory.call(null));
		lineComboBox.setCellFactory(cellFactory);
		vb.getChildren().addAll(lineComboBox);
		root.getChildren().add(vb);
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
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/mglass.gif")));
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

}
