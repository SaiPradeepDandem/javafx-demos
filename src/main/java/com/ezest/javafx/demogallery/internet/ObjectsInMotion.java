package com.ezest.javafx.demogallery.internet;

import java.util.Random;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
 
// animates a 1000 objects (klingons) moving around the scene at random directions and velocity.
public class ObjectsInMotion extends Application {
  private static Random random = new Random(42);
  private static final int    N_OBJECTS   = 1000;
  private static final int    OBJECT_SIZE = 20;
  private static final Image  KLINGON = new Image("http://icons.mysitemyway.com/wp-content/gallery/green-jelly-icons-transport-travel/038998-green-jelly-icon-transport-travel-star-trek-sc43.png", OBJECT_SIZE, OBJECT_SIZE, true, true);
  public static void main(String[] args) { launch(args); }
  @Override public void start(final Stage stage) throws Exception {
    // initialize the stage to fill the screen with klingons.
    stage.setTitle("Starboard bow");
    stage.setFullScreen(true);
    final double screenWidth    = Screen.getPrimary().getBounds().getWidth();
    final double screenHeight   = Screen.getPrimary().getBounds().getHeight();
    final Group objects = new Group(constructObjects(N_OBJECTS, OBJECT_SIZE, (int) screenWidth, (int) screenHeight));
    stage.setScene(new Scene(objects, screenWidth, screenHeight, Color.MIDNIGHTBLUE.darker().darker().darker()));
    stage.show();
    
    // press any key to exit the program.
    stage.getScene().setOnKeyTyped(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent event) {
        stage.close();
      }
    });
 
    // move the klingons around according to their motion vectors.
    final Timeline timeline = new Timeline(
      new KeyFrame(
        new Duration(1000/30), // update the klingon's motion 30 times a second.
        new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent event) {
            for (Node n: objects.getChildren()) {
              // apply the motion vector for this object to determine the object's new location.
              MotionVector vector = (MotionVector) n.getUserData();
              double tx = n.getTranslateX() + vector.velocity * Math.cos(Math.toRadians(vector.angle));
              double ty = n.getTranslateY() + vector.velocity * Math.sin(Math.toRadians(vector.angle));
              
              // wrap the objects around when they fall off the starfield.
              if (tx + n.getLayoutX() > screenWidth)  tx -= screenWidth;
              if (tx + n.getLayoutX() < 0)            tx += screenWidth;
              if (ty + n.getLayoutY() > screenHeight) ty -= screenHeight;
              if (ty + n.getLayoutY() < 0)            ty += screenHeight;
 
              // update the object co-ordinates.
              n.setTranslateX(tx);
              n.setTranslateY(ty);
            }
          }
        }
      )      
    );
    timeline.setRate(5);
    timeline.getCurrentRate();
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
  }
 
  // construct an array of n objects of rectangular size spaced randomly within a field of width and height.
  private Node[] constructObjects(final int n, final int size, final int width, final int height) {
    Node[] nodes = new Node[n];
    for (int i = 0; i < n; i++) {
      ImageView node = new ImageView(KLINGON);
      node.setLayoutX(random.nextInt(width  - size / 2));
      node.setLayoutY(random.nextInt(height - size / 2));
      MotionVector vector = new MotionVector();
      node.setUserData(vector);
      // rotate the klingon to align with the motion vector accounting for the klingon image initially pointing south-west.
      node.getTransforms().add(new Rotate(vector.angle + 225, node.getFitWidth() / 2, node.getFitHeight() / 2));  
      nodes[i] = node;
    }
    
    return nodes;
  }
 
  // polar co-ordinates of a motion vector.
  class MotionVector {
    final double velocity = random.nextDouble(); 
    final double angle    = random.nextDouble() * 360;
  }
}

