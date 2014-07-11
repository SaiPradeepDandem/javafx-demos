package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
 
public class ModalDialogueDemo extends Application {
 
  public void start(final Stage stage) throws Exception {
	Group root = new Group();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("WindowEvent06");
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      public void handle(final WindowEvent e){
    	e.consume(); 
    	new ModalDialog( stage, "Question");
      }
    });
    stage.show();
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}

