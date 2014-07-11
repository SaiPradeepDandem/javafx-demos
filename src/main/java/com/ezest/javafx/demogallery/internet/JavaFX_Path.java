package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
 
/**
*
*/
public class JavaFX_Path extends Application {
 
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      launch(args);
  }
 
  @Override
  public void start(Stage primaryStage) {
      primaryStage.setTitle("Path Demo");
      StackPane root = new StackPane();
      Scene scene = new Scene(root, 400, 300, Color.WHITE);
     
      Path path = new Path();
      path.getElements().add(new MoveTo(50, 50));
      path.getElements().add(new LineTo(50, 200));
      path.getElements().add(new LineTo(100, 200));
      path.getElements().add(new LineTo(100, 100));
      path.getElements().add(new LineTo(200, 100));
      path.setStrokeWidth(1);
      path.setStroke(Color.RED);
     
      Path arrow = new Path();
      arrow.getElements().add(new MoveTo(90, 50));
      arrow.getElements().add(new LineTo(50, 50));
      arrow.getElements().add(new LineTo(50, 100));
      arrow.getElements().add(new LineTo(63, 87));
      arrow.getElements().add(new LineTo(50, 100));
      arrow.getElements().add(new LineTo(37, 87));
      arrow.setStrokeWidth(1);
      arrow.setStroke(Color.BLACK);
      arrow.setRotate(135);
      
      Path arrow1 = new Path();
      arrow1.getElements().add(new MoveTo(40, 20));
      arrow1.getElements().add(new LineTo(25, 20));
      arrow1.getElements().add(new LineTo(25, 50));
      arrow1.getElements().add(new LineTo(35, 37));
      arrow1.getElements().add(new LineTo(25, 50));
      arrow1.getElements().add(new LineTo(15, 37));
      arrow1.setStrokeWidth(1);
      arrow1.setStroke(Color.GREEN);
      
      
      StackPane sp = new StackPane();
      sp.setPadding(new Insets(15,0,0,0));
      sp.setMaxWidth(40);
      sp.setAlignment(Pos.TOP_CENTER);
      
      Button printBtn = new Button("Print Me");
		printBtn.setRotate(-90);
		printBtn.setDisable(true);
		
		Group printCol = new Group();
		printCol.getChildren().addAll(printBtn);
		printCol.setTranslateY(40);
		
		sp.getChildren().addAll(arrow1,printCol);
		
      root.getChildren().addAll(path);
      primaryStage.setScene(scene);
      primaryStage.show();
 
  }
}
