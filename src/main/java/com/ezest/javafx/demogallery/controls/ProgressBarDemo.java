package com.ezest.javafx.demogallery.controls;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class ProgressBarDemo extends Application {

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
		
		VBox vb = VBoxBuilder.create().spacing(15).padding(new Insets(15)).build();
		root.getChildren().add(vb);
		
		ProgressBar defaultProgressBar = new ProgressBar();
		
		ProgressBar progress1 = getProgress();
		progress1.setProgress(.2F);
		
		ProgressBar progress2 = getProgress();
		progress2.setProgress(.6F);
		
		ProgressBar progress3 = getProgress();
		progress3.setProgress(.9F);
		
		vb.getChildren().addAll(defaultProgressBar, progress1, progress2, progress3);
		
		Label lbl = new Label();
		lbl.setWrapText(true);
		
	}
	
	public ProgressBar getProgress(){
		final ProgressBar progressBar = new ProgressBar();
		progressBar.setPrefWidth(300);
		progressBar.getStyleClass().addAll("my-progress-bar");
		progressBar.progressProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0,	Number arg1, Number value) {
				progressBar.getStyleClass().removeAll("red-progress-bar","blue-progress-bar","green-progress-bar");
				if(value.doubleValue()<.3){
					progressBar.getStyleClass().add("green-progress-bar");
				}else if(value.doubleValue()<.7){
					progressBar.getStyleClass().add("blue-progress-bar");
				}else{
					progressBar.getStyleClass().add("red-progress-bar");
				}
			}
		});
		return progressBar;
	}

	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(500);
	    stage.setHeight(500);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setBottom(getBottom());
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
		scene.getStylesheets().add("styles/progressbar.css");
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
		sp.getChildren().addAll(new Separator(),image);
		return sp;
	}

}


