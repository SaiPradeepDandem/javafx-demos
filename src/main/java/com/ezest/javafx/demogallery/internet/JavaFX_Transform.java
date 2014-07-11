package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
 
/**
*
* @web http://java-buddy.blogspot.com/
*/
public class JavaFX_Transform extends Application {
 
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      launch(args);
  }
 
  private static class Reactangle {
 
      public Reactangle() {
      }
  }
 
  @Override
  public void start(Stage primaryStage) {
      primaryStage.setTitle("java-buddy.blogspot.com");
      Group root = new Group();
      Scene scene = new Scene(root, 300, 250);
 
      Rectangle rect1 = new Rectangle(100, 100, Color.BLUE);  //without translate
      Rectangle rect2 = new Rectangle(100, 100, Color.RED);   //with translate
      rect2.getTransforms().add(new Translate(150, 50));          //Translate(double x, double y)
     
      root.getChildren().add(rect1);
      root.getChildren().add(rect2);
      primaryStage.setScene(scene);
      primaryStage.show();
  }
}
