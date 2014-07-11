package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class ScrollPaneScrollToEnd extends Application {
  private boolean scrollToBottom = false;
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(final Stage stage) throws Exception {
 
        final VBox root = new VBox();
 
        final ScrollPane scrollPane = new ScrollPane();
 
        final VBox vBox = new VBox();
        vBox.setAlignment(Pos.BOTTOM_CENTER);
 
        scrollPane.setContent(vBox);
 
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
 
        Button button = new Button("add");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vBox.getChildren().add(new Label("hallo"));
                scrollToBottom = true;
                //System.out.println(scrollPane.getVmin() + "; max = " + scrollPane.getVmax() + "; " + scrollPane.getVvalue());
            }
        });
 
        scrollPane.setVvalue(scrollPane.getVmax());
        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            if(scrollToBottom) {
              scrollPane.setVvalue(scrollPane.getVmax());
              scrollToBottom = false;
            }
          }
        });
 
        Button button2 = new Button("scroll");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scrollPane.setVvalue(Double.MAX_VALUE);
            }
        });
 
        root.getChildren().addAll(scrollPane, button, button2);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
