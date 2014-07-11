package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.concurrent.Worker;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.*;
 
/** Demonstrates a modal confirm box in JavaFX.
    Dialog is rendered upon a blurred background.
    Dialog is translucent. */
public class ModalConfirmExample extends Application {
  public static void main(String[] args) { launch(args); }
  @Override public void start(final Stage primaryStage) {
    // initialize the stage
    primaryStage.setTitle("Modal Confirm Example");
    final WebView webView = new WebView(); webView.getEngine().load("http://docs.oracle.com/javafx/");
    primaryStage.setScene(new Scene(webView));
    primaryStage.show();
 
    // initialize the confirmation dialog
    final Stage util = new Stage(StageStyle.TRANSPARENT);
    util.initModality(Modality.APPLICATION_MODAL);
    util.setScene(
      new Scene(
        HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(
          LabelBuilder.create().text("Will you like this page?").build(),
          ButtonBuilder.create().text("Yes").defaultButton(true).onAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
              // take action and close the dialog.
              System.out.println("Liked: " + webView.getEngine().getTitle());
              primaryStage.getScene().getRoot().setEffect(null);
              util.close();
            }
          }).build(),
          ButtonBuilder.create().text("No").cancelButton(true).onAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
              // abort action and close the dialog.
              System.out.println("Disliked: " + webView.getEngine().getTitle());
              primaryStage.getScene().getRoot().setEffect(null);
              util.close();
            }
          }).build()
        ).style("-fx-padding: 20; -fx-font-size: 20; -fx-background-color: linear-gradient(to bottom, derive(cadetblue, 20%), cadetblue); -fx-border-color: derive(cadetblue, -20%); -fx-border-width: 5;")
         .build()
      )
    );
    util.getScene().setFill(Color.TRANSPARENT);
    util.getScene().getRoot().setOpacity(0.9);
 
    // show the confirmation dialog each time a new page is loaded.
    webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
      @Override public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State newState) {
        if (newState.equals(Worker.State.SUCCEEDED)) {
          primaryStage.getScene().getRoot().setEffect(new BoxBlur());
          util.show();
          util.toFront();
        }
      }
    });
  }
}

