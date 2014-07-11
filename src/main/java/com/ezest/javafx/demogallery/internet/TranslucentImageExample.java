package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class TranslucentImageExample extends Application {
  @Override public void start(final Stage stage) throws Exception {
    ImageView imageView = new ImageView(new Image("D:\\My Pics\\explorator-wallpapers_19483_1280x800.jpg"));
    imageView.setViewport(new Rectangle2D(0, 0, 800, 600));
 
    final StackPane layout = new StackPane();
    layout.getChildren().addAll(new Label("Hello dude"),imageView);
    layout.setStyle("-fx-background-color: cornsilk; -fx-padding:10");
    stage.setScene(new Scene(layout));
    stage.show();
 
    imageView.setOpacity(0.5);
  }
  public static void main(String[] args) throws Exception { launch(args); }
}

