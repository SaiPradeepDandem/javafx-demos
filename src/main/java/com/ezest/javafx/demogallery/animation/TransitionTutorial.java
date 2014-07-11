package com.ezest.javafx.demogallery.animation;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class TransitionTutorial extends Application {
 
    @Override
    public void start(Stage primaryStage) {
 
        //Create the rectangle using a builder
        final Rectangle rectangle = RectangleBuilder.create()
                .width(200).height(200)
                .arcHeight(10).arcWidth(10)
                .fill(Color.AQUA).build();
 
       //Create a simple button with listener to trigger transition
        Button btn1 = new Button("Fade Transition");        
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	FadeTransition fadeObject= new FadeTransition(Duration.seconds(3), rectangle);
            	fadeObject.setFromValue(0);
            	fadeObject.setToValue(1);
            	fadeObject.play();
            }
        });
 
        Button btn2 = new Button("Slide In Transition");        
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	TranslateTransition slideIn = new TranslateTransition(Duration.seconds(2), rectangle);
            	slideIn.setByX(0);
            	slideIn.play();
            }
        });
 
        Button btn3 = new Button("Slide Out Transition");        
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	TranslateTransition slideOut = new TranslateTransition(Duration.seconds(2), rectangle);
            	slideOut.setByX(250);
            	slideOut.play();
            }
        });
 
        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.getChildren().addAll(btn1, btn2, btn3);
        
        BorderPane root = new BorderPane();
        root.setCenter(rectangle);
        root.setBottom(hb);
        //Sets the alignment of the button
        
 
        Scene scene = new Scene(root,300,250,Color.ANTIQUEWHITE);
 
        primaryStage.setTitle("Transition Tutorial");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
}