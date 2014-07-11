package com.ezest.javafx.demogallery.shapes;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Arrow extends Application {

	Stage stage;
	Scene scene;
	VBox root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		// Rendering the arrow shape
				Path arrow = new Path();
				arrow.getElements().add(new MoveTo(40, 20));
				arrow.getElements().add(new LineTo(27, 20));
				arrow.getElements().add(new LineTo(27, 50));
				arrow.getElements().add(new LineTo(34, 40));
				arrow.getElements().add(new LineTo(27, 50));
				arrow.getElements().add(new LineTo(20, 40));
				arrow.setStrokeWidth(1);
				arrow.setStroke(Color.BLACK);
				
				Path arrow1 = new Path();
				arrow1.getElements().add(new MoveTo(0, 60)); //M 0 60
				arrow1.getElements().add(new LineTo(0, 70)); //V 70
				arrow1.getElements().add(new LineTo(60, 130)); // L 60 130
				arrow1.getElements().add(new LineTo(80, 110)); // L 80 110
				arrow1.getElements().add(new LineTo(40, 70)); // L 40 70
				arrow1.getElements().add(new LineTo(40, 60)); // V 60
				arrow1.getElements().add(new LineTo(80, 20)); // L 80 20
				arrow1.getElements().add(new LineTo(60, 0));  // L 60 0
				arrow1.getElements().add(new LineTo(0, 60)); // L 0 60 Z
				arrow1.getElements().add(new LineTo(0, 70));
				arrow1.setStrokeWidth(1);
				arrow1.setStroke(Color.BLACK);
				arrow1.setFill(Color.WHITE);
				
				Path arrow2 = new Path();
				arrow2.getElements().add(new MoveTo(0, 6));
				arrow2.getElements().add(new LineTo(0, 7));
				arrow2.getElements().add(new LineTo(6, 13));
				arrow2.getElements().add(new LineTo(8, 11));
				arrow2.getElements().add(new LineTo(4, 7));
				arrow2.getElements().add(new LineTo(4, 6));
				arrow2.getElements().add(new LineTo(8, 2));
				arrow2.getElements().add(new LineTo(6, 0));
				arrow2.getElements().add(new LineTo(0, 6));
				arrow2.getElements().add(new LineTo(0, 7));
				arrow2.setStrokeWidth(1);
				arrow2.setStroke(Color.BLACK);
				arrow2.setFill(Color.WHITE);
				
				StackPane leftSide = new StackPane();
				leftSide.setPrefSize(8, 13);
				leftSide.getStyleClass().add("small-bended-arrow");
				
				Rectangle p = new Rectangle();
				p.getStyleClass().add("small-bended-arrow-rect");
				
				Path downArrow = new Path();
				downArrow.getElements().add(new MoveTo(0, 0));
				downArrow.getElements().add(new LineTo(0, 1));
				downArrow.getElements().add(new LineTo(5, 6));
				downArrow.getElements().add(new LineTo(6, 6));
				downArrow.getElements().add(new LineTo(11, 1));
				downArrow.getElements().add(new LineTo(11, 0));
				downArrow.getElements().add(new LineTo(0, 0));
				downArrow.setStrokeWidth(.9);
				downArrow.setStroke(Color.BLACK);
				downArrow.setFill(Color.WHITE);
				
				root.getChildren().addAll(GroupBuilder.create().children(leftSide).build(),arrow1,downArrow);
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
		root = new VBox();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/shapes.css");
	}

}

