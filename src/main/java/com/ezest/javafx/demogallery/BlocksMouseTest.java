package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
*
* @author Narayan
*/
public class BlocksMouseTest extends Application{

   @Override
   public void start(Stage primaryStage) throws Exception {
       Group g = new Group();
       final Scene scene = new Scene(g,500,500);

       //Creating Two Circles
       final Circle top = new Circle();
       Circle bottom = new Circle();

       //Some attributes setting
       top.setRadius(20);
       top.setCenterX(200);
       top.setCenterY(200);
       top.setFill(Color.YELLOW);

       bottom.setRadius(30);
       bottom.setCenterX(200);
       bottom.setCenterY(200);
       bottom.setFill(Color.BLUE);

       //Some mouse events stuffs
       top.setOnMousePressed(new EventHandler<MouseEvent>(){
           public void handle(MouseEvent event) {
               System.out.println("TOP Pressed" );
           }

       });
       bottom.setOnMouseEntered(new EventHandler<MouseEvent>(){

           public void handle(MouseEvent event) {
               System.out.println("BOTTOM Entered");
           }

       });
       bottom.setOnMousePressed(new EventHandler<MouseEvent>(){

           public void handle(MouseEvent event) {
               System.out.println("BOTTOM Pressed" );
           }

       });

       //Keeping Circle inside BlocksMouseNode
       BlocksMouseNode blockMouseNode = new BlocksMouseNode();
       blockMouseNode.setBlocksMouse(true);
       blockMouseNode.getChildren().addAll(top);

       //Again making those nodes Draggable
      /* DraggableNode bottomPane = new DraggableNode();
       bottomPane.getChildren().addAll(bottom);

       DraggableNode topPane = new DraggableNode();
       topPane.getChildren().addAll(blockMouseNode);

       //finally added to root
       g.getChildren().addAll(bottomPane,topPane);*/
       g.getChildren().addAll(bottom,top);

       primaryStage.setScene(scene);
       primaryStage.show();
   }

   public static void main(String[] args) {
       launch(args);
   }
}
