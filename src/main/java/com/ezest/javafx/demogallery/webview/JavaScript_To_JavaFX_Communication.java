package com.ezest.javafx.demogallery.webview;

import java.net.URL;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
 
public class JavaScript_To_JavaFX_Communication extends Application {
     
    private Scene scene;
    MyBrowser myBrowser;
     
    Label labelFromJavascript;
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaScript_To_JavaFX_Communication Demo");
         
        myBrowser = new MyBrowser();
        scene = new Scene(myBrowser, 640, 480);
         
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     
    class MyBrowser extends Region{
         
        HBox toolbar;
        VBox toolbox;
         
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
         
        public MyBrowser(){
             
            final URL urlHello = getClass().getResource("JavaScript_To_JavaFX.html");
             
            
            webEngine.load(urlHello.toExternalForm());
            
            webEngine.getLoadWorker().stateProperty().addListener(
                    new ChangeListener<State>(){
                         
                        @Override
                        public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
                            if(newState == State.SUCCEEDED){
                                JSObject window = (JSObject)webEngine.executeScript("window");
                                window.setMember("app", new JavaApplication());
                            }
                        }
                    });
             
             /*
            JSObject window = (JSObject)webEngine.executeScript("window");
            window.setMember("app", new JavaApplication());
            */
            final TextField textField = new TextField ();
            textField.setPromptText("Hello! Who are?");
             
            Button buttonEnter = new Button("Enter");
            buttonEnter.setOnAction(new EventHandler<ActionEvent>(){
                 
                @Override
                public void handle(ActionEvent arg0) {
                    webEngine.executeScript( " updateHello(' " + textField.getText() + " ') " );
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
             
            toolbox = new VBox();
            labelFromJavascript = new Label();
            toolbox.getChildren().addAll(toolbar, labelFromJavascript);
            labelFromJavascript.setText("Wait");
             
            getChildren().add(toolbox);
            getChildren().add(webView);
             
        }
         
        @Override
        protected void layoutChildren(){
            double w = getWidth();
            double h = getHeight();
            double toolboxHeight = toolbox.prefHeight(w);
            layoutInArea(webView, 0, 0, w, h-toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
            layoutInArea(toolbox, 0, h-toolboxHeight, w, toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
        }
         
    }
     
    public class JavaApplication {
        public void callFromJavascript(String msg) {
            labelFromJavascript.setText("Click from Javascript: " + msg);
        }
    }
     
}