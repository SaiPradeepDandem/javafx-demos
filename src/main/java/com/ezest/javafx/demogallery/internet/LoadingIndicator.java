package com.ezest.javafx.demogallery.internet;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoadingIndicator extends Parent{
   
   private Timeline timeline = new Timeline();
   
   private DoubleProperty stroke = new SimpleDoubleProperty(100.0);
   
   
   public LoadingIndicator(){
       super();
       
       timeline.setCycleCount(Timeline.INDEFINITE);
       
       final KeyValue kv = new KeyValue(stroke, 0);
       final KeyFrame kf = new KeyFrame(Duration.millis(1500), kv);
       
       timeline.getKeyFrames().add(kf);
       timeline.play();        
       
       VBox root = new VBox(3);
       
       StackPane progressIndicator = new StackPane();
       
       Rectangle bar = new Rectangle(350,13);
       bar.setFill(Color.TRANSPARENT);
       bar.setStroke(Color.WHITE);
       bar.setArcHeight(15);
       bar.setArcWidth(15);
       bar.setStrokeWidth(2);
       
       Rectangle progress = new Rectangle(342,6);
       progress.setFill(Color.WHITE);
       progress.setStroke(Color.WHITE);
       progress.setArcHeight(8);
       progress.setArcWidth(8);
       progress.setStrokeWidth(1.5);
       progress.getStrokeDashArray().addAll(3.0,7.0,3.0,7.0);
       progress.strokeDashOffsetProperty().bind(stroke);
       
       
       progressIndicator.getChildren().add(progress);
       progressIndicator.getChildren().add(bar);
       
       root.getChildren().add(progressIndicator);
       
       Text label = new Text("Loading...");
       label.setFill(Color.WHITE);
       
       root.getChildren().add(label);
       
       getChildren().add(root);
       
   }
   
}

