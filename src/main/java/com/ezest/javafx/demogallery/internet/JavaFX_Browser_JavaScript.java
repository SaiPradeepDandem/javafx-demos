package com.ezest.javafx.demogallery.internet;

import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
 
/**
*
* @web http://java-buddy.blogspot.com/
*/
public class JavaFX_Browser_JavaScript extends Application {
 
  private Scene scene;
  MyBrowser myBrowser;
 
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
      launch(args);
  }
 
  @Override
  public void start(Stage primaryStage) {
      primaryStage.setTitle("java-buddy.blogspot.com");
     
      myBrowser = new MyBrowser();
      scene = new Scene(myBrowser, 640, 480);
     
      primaryStage.setScene(scene);
      primaryStage.show();
  }
 
  class MyBrowser extends Region{
     
      HBox toolbar;
     
      WebView webView = new WebView();
      WebEngine webEngine = webView.getEngine();
     
      public MyBrowser(){
         
          final URL urlHello = getClass().getResource("hello.html");
          webEngine.load(urlHello.toExternalForm());
          
          webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
				@Override
				public void handle(WebEvent<String> paramT) {
					System.out.println(paramT.getData());
				}
			 });
          
          final TextField textField = new TextField ();
          textField.setPromptText("Hello! Who are?");
         
          Button buttonEnter = new Button("Enter");
          buttonEnter.setOnAction(new EventHandler<ActionEvent>(){
 
              @Override
              public void handle(ActionEvent arg0) {
                 // webEngine.executeScript( " updateHello(' " + textField.getText() + " ') " );
            	  webEngine.loadContent(genHtml());
              }
          });
         
          Button buttonClear = new Button("Clear");
          buttonClear.setOnAction(new EventHandler<ActionEvent>(){
 
              @Override
              public void handle(ActionEvent arg0) {
                  webEngine.executeScript( "clearHello()" );
              }
          });
         
         
          toolbar = new HBox();
          toolbar.setPadding(new Insets(10, 10, 10, 10));
          toolbar.setSpacing(10);
          toolbar.setStyle("-fx-background-color: #336699");
          toolbar.getChildren().addAll(textField, buttonEnter, buttonClear);
         
          getChildren().add(toolbar);
          getChildren().add(webView);
      }
     
      private String genHtml(){
    	   
    	     StringBuilder tHtml = new StringBuilder();
    	   
    	     tHtml.append("<DOCTYPE html>" + "\n");
    	     tHtml.append("<html lang='en-US'>" + "\n");
    	     tHtml.append("<head>" + "\n");
    	     tHtml.append("<meta charset=utf-8>" + "\n");
    	     tHtml.append("<title>Hello Java-Buddy!</title>" + "\n");
    	     tHtml.append("</head>" + "\n");
    	     tHtml.append("<body>" + "\n");
    	     tHtml.append("<p>Hello <a href='http://java-buddy.blogspot.com/'>Java-Buddy This is great</a></p>" + "\n");
    	     tHtml.append("<img src='http://2.bp.blogspot.com/-Ol8pLJcc9oo/TnZY6R8YJ5I/AAAAAAAACSI/YDxcIHCZhy4/s150/duke_44x80.png'/>" + "\n");
    	     tHtml.append("<input type='button' value='showAlert' onclick='alert(3)'>" + "\n");
    	     tHtml.append("</body>" + "\n");
    	     tHtml.append("</html>" + "\n");
    	   
    	     return tHtml.toString();    
    	 }
      @Override
      protected void layoutChildren(){
          double w = getWidth();
          double h = getHeight();
          double toolbarHeight = toolbar.prefHeight(w);
          layoutInArea(webView, 0, 0, w, h-toolbarHeight, 0, HPos.CENTER, VPos.CENTER);
          layoutInArea(toolbar, 0, h-toolbarHeight, w, toolbarHeight, 0, HPos.CENTER, VPos.CENTER);
      }
     
  }
 
}
