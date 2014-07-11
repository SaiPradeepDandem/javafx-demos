package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
*
* @web http://java-buddy.blogspot.com/
*/
public class JavaFX_DragAndDrop extends Application {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       launch(args);
   }
  
   @Override
   public void start(Stage primaryStage) {
       primaryStage.setTitle("java-buddy.blogspot.com");
       Group root = new Group();
       Scene scene = new Scene(root, 350, 300);
      
       final Text text1 = new Text(50, 100, "TEXT 1");
       text1.setScaleX(2.0);
       text1.setScaleY(2.0);

       final Text text2 = new Text(250, 100, "TEXT 2");
       text2.setScaleX(2.0);
       text2.setScaleY(2.0);
      
       final Text text3 = new Text(50, 200, "TEXT 3");
       text3.setScaleX(2.0);
       text3.setScaleY(2.0);

       final Text text4 = new Text(250, 200, "TEXT 4");
       text4.setScaleX(2.0);
       text4.setScaleY(2.0);
      
       setupGestureSource(text1);
       setupGestureTarget(text1);
       setupGestureSource(text2);
       setupGestureTarget(text2);
       setupGestureSource(text3);
       setupGestureTarget(text3);
       setupGestureSource(text4);
       setupGestureTarget(text4);
      
       root.getChildren().addAll(text1, text2, text3, text4);
       primaryStage.setScene(scene);
       primaryStage.show();
       primaryStage.show();
   }
  
   void setupGestureSource(final Text source){
      
       source.setOnDragDetected(new EventHandler <MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               /* drag was detected, start drag-and-drop gesture*/
               System.out.println("onDragDetected");
              
               /* allow any transfer mode */
               Dragboard db = source.startDragAndDrop(TransferMode.ANY);
              
               /* put a string on dragboard */
               ClipboardContent content = new ClipboardContent();
               content.putString(source.getText());
               db.setContent(content);
              
               event.consume();
           }
       });
      
       source.setOnDragDone(new EventHandler <DragEvent>() {
           @Override
           public void handle(DragEvent event) {
               /* the drag-and-drop gesture ended */
               System.out.println("onDragDone");
               /* if the data was successfully moved, clear it */
               if (event.getTransferMode() == TransferMode.MOVE) {
                   source.setText("");
               }
              
               event.consume();
           }
       });
      
   }
  
   void setupGestureTarget(final Text target){
      
       target.setOnDragOver(new EventHandler <DragEvent>() {
           @Override
           public void handle(DragEvent event) {
               /* data is dragged over the target */
               System.out.println("onDragOver");
              
               /* accept it only if it is  not dragged from the same node
                * and if it has a string data */
               if (event.getGestureSource() != target &&
                       event.getDragboard().hasString()) {
                   /* allow for both copying and moving, whatever user chooses */
                   event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
               }
              
               event.consume();
           }
       });

       target.setOnDragEntered(new EventHandler <DragEvent>() {
           @Override
           public void handle(DragEvent event) {
               /* the drag-and-drop gesture entered the target */
               System.out.println("onDragEntered");
               /* show to the user that it is an actual gesture target */
               if (event.getGestureSource() != target &&
                       event.getDragboard().hasString()) {
                   target.setFill(Color.GREEN);
               }
              
               event.consume();
           }
       });

       target.setOnDragExited(new EventHandler <DragEvent>() {
           @Override
           public void handle(DragEvent event) {
               /* mouse moved away, remove the graphical cues */
               target.setFill(Color.BLACK);
              
               event.consume();
           }
       });
      
       target.setOnDragDropped(new EventHandler <DragEvent>() {
           @Override
           public void handle(DragEvent event) {
               /* data dropped */
               System.out.println("onDragDropped");
               /* if there is a string data on dragboard, read it and use it */
               Dragboard db = event.getDragboard();
               boolean success = false;
               if (db.hasString()) {
                   target.setText(target.getText() + "\n" + db.getString());
                   success = true;
               }
               /* let the source know whether the string was successfully
                * transferred and used */
               event.setDropCompleted(success);
              
               event.consume();
           }
       });
      
   }
}
