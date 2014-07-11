package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NoCRTextArea extends Application {
	 
	  TextArea textArea;
	  ChangeListener<String> listener;
	 
	  public static void main(String[] args) {
	    Application.launch(args);
	  }
	 
	  @Override
	  public void start(Stage primaryStage) throws Exception {
	    textArea = new TextArea("I am check");
	    textArea.selectRange(2, 6);
	    
	    listener = new ChangeListener<String>() {
	 
	      @Override
	      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	        Platform.runLater(new Runnable() {
	 
	          @Override
	          public void run() {
	            String text = textArea.getText();
	            if (text.indexOf("\n") > -1) {
	              textArea.setText(text.replaceAll("[\n\r]", ""));
	            }
	          }
	        });
	      }
	    };
	    //textArea.textProperty().addListener(listener);
	    textArea.setWrapText(true);
	    textArea.getStyleClass().add("disabledTextArea");
	    //textArea.setDisable(true);
	    
	 
	    TextField tf = new TextField("hello");
	    tf.getStyleClass().add("disabledTextField");
	    tf.setDisable(true);
	    
	    HBox pane = new HBox();
	    pane.getChildren().addAll(textArea,tf);
	    
	    Button btn = new Button();
	    btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent paramT) {
				//textArea.appendText("I am added /n");
				String s = textArea.getText();
				String[] arr = s.split((new Character((char)10)).toString());
				System.out.println(arr.length);
			}
		});
	    
	    VBox vb = new VBox();
	    vb.getChildren().addAll(btn, pane);
	    
	    StackPane sp = new StackPane();
	    sp.setPadding(new Insets(15));
	    sp.getChildren().add(vb);
	    Scene scene = new Scene(sp);
	    scene.getStylesheets().add("styles/template.css");
	    
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
	    for (String c : textArea.getStyleClass()) {
	    	System.out.println(c);
			
		}
	  }
	}