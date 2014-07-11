package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPaneBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.ezest.javafx.components.DragResizer;
import com.javafx.experiments.scenicview.ScenicView;

public class DragResizerDemo extends Application {

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
		
		
		StringBuilder sb = new StringBuilder("-fx-background-color:linear-gradient(from 0px -19px to 0px 0px , repeat, #EEEEEE 76% , #CCC9C1 79% , #FFF0C7 89% );");
		sb.append("-fx-border-color:#CCC9C1;");
		sb.append("-fx-border-width:0px 1.5px 0px 1.5px;");
		sb.append("-fx-border-style: segments(62,14) phase 35;");
		
		Region r = new Region();
		r.setPrefWidth(200);
		r.setMaxSize(100, 100);
		r.setStyle(sb.toString());
		DragResizer.makeResizable(r);
		HBox hb = new HBox();
		hb.setSpacing(20);
		hb.getChildren().addAll(r);
		root.getChildren().add(hb);
	}

	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
	}

}
