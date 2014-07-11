package com.ezest.javafx.sscce;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationClipBounds extends Application
{
    public static void main(String[] args) throws Exception
    {
        launch(args);
    }
 
    public void start(Stage stage) throws Exception
    {
        BorderPane contentPane = new BorderPane();
 
        HBox top = new HBox();
        top.getChildren().add(new Label("Top Area"));
        top.setStyle("-fx-background-color: blue");
        top.setPrefHeight(100);
        contentPane.setTop(top);
 
        HBox bottom = new HBox();
        bottom.getChildren().add(new Label("Green Area"));
        bottom.setStyle("-fx-background-color: green");
        bottom.setPrefHeight(100);
        contentPane.setBottom(bottom);
 
        HBox left = new HBox();
        left.getChildren().add(new Label("Green Area"));
        left.setStyle("-fx-background-color: yellow");
        left.setPrefWidth(100);
        contentPane.setLeft(left);
 
        HBox right = new HBox();
        right.getChildren().add(new Label("Right Area"));
        right.setStyle("-fx-background-color: red");
        right.setPrefWidth(100);
        contentPane.setRight(right);
 
        FlowPane animationArea = new FlowPane();
        Label label = new Label("Look at me, I'm flying!");
        label.setStyle("-fx-background-color: #ffd; -fx-background-radius: 3; -fx-border-color: gray; -fx-background-radius: 3; ");
        animationArea.getChildren().add(label);
        contentPane.setCenter(animationArea);
 
        Scene scene = new Scene(contentPane, 800, 600);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
 
        TranslateTransition animation = new TranslateTransition(Duration.millis(2000), label);
        animation.setFromX(-100);
        animation.setToX(800);
        animation.setFromY(-400);
        animation.setToY(400);
        animation.setAutoReverse(true);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.playFromStart();
    }
}

