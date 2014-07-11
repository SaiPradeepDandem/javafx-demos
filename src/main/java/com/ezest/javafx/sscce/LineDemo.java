package com.ezest.javafx.sscce;

import com.javafx.experiments.scenicview.ScenicView;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
 
public class LineDemo extends Application {
 
private void setTranslateXY(Shape shape, double x, double y){
shape.setTranslateX(x);
shape.setTranslateY(y);
}
 
private void setTranslateXY(Node container, double x, double y){
container.setTranslateX(x);
container.setTranslateY(y);
}
 
@Override
public void start(Stage stage) {
	Group root = new Group();
	Scene scene = new Scene(root, 500, 500, Color.BLACK);
 
	Ellipse circle1=new Ellipse(0f,0f,50f,50f);
	circle1.setStroke(Color.BLUE);
 
	Line line2 = new Line(-50,-50,50,50);
	Line line3 = new Line(0,-50,0,50);
 
	line2.setFill(Color.YELLOW);
	line2.setStroke(Color.YELLOW);
	line3.setFill(Color.BLUE);
	line3.setStroke(Color.BLUE);
 
	setTranslateXY(root,150,150);
 
	root.getChildren().addAll(circle1,line2,line3);
 
	stage.setMinWidth(640);
	stage.setMinHeight(400);
	stage.setTitle("JavaFX Scene Graph Demo-line1.java");
	stage.setScene(scene);
	stage.show();
	ScenicView.show(scene);
   }
 
public static void main(String[] args) {
	launch(args);
}
}

