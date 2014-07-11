package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
 
public class LayoutBoundsScrollableAnchorPane extends Application  {
  // define some controls.
  final ToggleButton stroke    = new ToggleButton("Add Border");
  final ToggleButton effect    = new ToggleButton("Add Effect");
  final ToggleButton translate = new ToggleButton("Translate");
  final ToggleButton rotate    = new ToggleButton("Rotate");
  final ToggleButton scale     = new ToggleButton("Scale");
 
  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage stage) throws Exception {
    // create a square to be acted on by the controls.
    final Rectangle square = new Rectangle(20, 30, 100, 100); //square.setFill(Color.DARKGREEN);
    square.setStyle("-fx-fill: linear-gradient(to right, darkgreen, forestgreen)");
    
    // show the effect of a stroke.
    stroke.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent actionEvent) {
        if (stroke.isSelected()) {
          square.setStroke(Color.FIREBRICK); square.setStrokeWidth(10); square.setStrokeType(StrokeType.OUTSIDE);
        } else {
          square.setStroke(null); square.setStrokeWidth(0.0); square.setStrokeType(null);
        }
        reportBounds(square);
      }
    });
 
    // show the effect of an effect.
    effect.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent actionEvent) {
        if (effect.isSelected()) {
          square.setEffect(new DropShadow());
        } else {
          square.setEffect(null);
        }
        reportBounds(square);
      }
    });
 
    // show the effect of a translation.
    translate.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent actionEvent) {
        if (translate.isSelected()) {
          square.setTranslateX(100);
          square.setTranslateY(60);
        } else {
          square.setTranslateX(0);
          square.setTranslateY(0);
        }
        reportBounds(square);
      }
    });
 
    // show the effect of a rotation.
    rotate.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent actionEvent) {
        if (rotate.isSelected()) {
          square.setRotate(45);
        } else {
          square.setRotate(0);
        }
        reportBounds(square);
      }
    });
 
    // show the effect of a scale.
    scale.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent actionEvent) {
        if (scale.isSelected()) {
          square.setScaleX(2);
          square.setScaleY(2);
        } else {
          square.setScaleX(1);
          square.setScaleY(1);
        }
        reportBounds(square);
      }
    });
 
    // layout the scene.
    final AnchorPane anchorPane = new AnchorPane();
    AnchorPane.setTopAnchor(square,  0.0);
    AnchorPane.setLeftAnchor(square, 0.0);
    anchorPane.setStyle("-fx-background-color: cornsilk;");
    anchorPane.getChildren().add(square);
 
    // add a scrollpane and size it's content to fit the pane (if it can).
    final ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(anchorPane);
    square.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
      @Override public void changed(ObservableValue<? extends Bounds> observableValue, Bounds oldBounds, Bounds newBounds) {
        anchorPane.setPrefSize(Math.max(newBounds.getMaxX(), scrollPane.getViewportBounds().getWidth()), Math.max(newBounds.getMaxY(), scrollPane.getViewportBounds().getHeight()));
      }
    });
    scrollPane.viewportBoundsProperty().addListener(
      new ChangeListener<Bounds>() {
      @Override public void changed(ObservableValue<? extends Bounds> observableValue, Bounds oldBounds, Bounds newBounds) {
        anchorPane.setPrefSize(Math.max(square.getBoundsInParent().getMaxX(), newBounds.getWidth()), Math.max(square.getBoundsInParent().getMaxY(), newBounds.getHeight()));
      }
    });
 
    // layout the scene.
    VBox controlPane = new VBox(10);
    controlPane.setStyle("-fx-background-color: linear-gradient(to bottom, gainsboro, silver); -fx-padding: 10;");
    controlPane.getChildren().addAll(
      HBoxBuilder.create().spacing(10).children(stroke, effect).build(),
      HBoxBuilder.create().spacing(10).fillHeight(false).children(translate, rotate, scale).build()
    );
 
    VBox layout = new VBox();
    VBox.setVgrow(scrollPane, Priority.ALWAYS);
    layout.getChildren().addAll(scrollPane, controlPane);
 
    // show the scene.
    final Scene scene = new Scene(layout, 300, 300);
    stage.setScene(scene);
    stage.show();
 
    reportBounds(square);
  }
 
  /** output the squares bounds. */
  private void reportBounds(final Node n) {
    StringBuilder description = new StringBuilder();
    if (stroke.isSelected())       description.append("Stroke 10 : ");
    if (effect.isSelected())       description.append("Dropshadow Effect : ");
    if (translate.isSelected())    description.append("Translated 100, 60 : ");
    if (rotate.isSelected())       description.append("Rotated 45 degrees : ");
    if (scale.isSelected())        description.append("Scale 2 : ");
    if (description.length() == 0) description.append("Unchanged : ");
 
    System.out.println(description.toString());
    System.out.println("Layout Bounds:    " + n.getLayoutBounds());
    System.out.println("Bounds In Local:  " + n.getBoundsInLocal());
    System.out.println("Bounds In Parent: " + n.getBoundsInParent());
    System.out.println();
  }
}

