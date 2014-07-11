package com.ezest.javafx.demogallery.panes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
 
 
public class MutableGridPane extends Application {
  
  private final int NUM_COLS = 8 ;
 
  @Override
  public void start(Stage primaryStage) throws Exception {
    final GridPane gridPane = new GridPane();
    final ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(gridPane);
    addHeaderRow(gridPane);
    
    // Add 250 rows to test performance:
    for (int i=0; i<250; i++) {
      addNewRow(gridPane, 1);
    }
    
    final Button addButton = new Button("Add a row");
    
    addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        addNewRow(gridPane, 1);
      }
    });
    
    final BorderPane root = new BorderPane();
    root.setCenter(scrollPane);
    root.setBottom(addButton);
    
    Scene scene = new Scene(root, 600, 600);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
  private void addHeaderRow(GridPane gridPane) {
    for (Node node : gridPane.getChildren()) {
      GridPane.setRowIndex(node, GridPane.getRowIndex(node)+1);
    }
    for (int colIndex=0; colIndex<NUM_COLS; colIndex++) {
      Label header = new Label("Header "+(colIndex+1));
      header.setStyle("-fx-background-color: lightgray;");
      header.setMaxWidth(Double.POSITIVE_INFINITY);
      gridPane.add(header, colIndex, 0);
    }
  }
  
  private void addNewRow(GridPane gridPane, int rowIndex) {
    int numRows = 1 ;
    for (Node node : gridPane.getChildren()) {
      int currentRow = GridPane.getRowIndex(node);
      if (currentRow >= rowIndex) {
        GridPane.setRowIndex(node, currentRow+1);
        if (currentRow+1 > numRows) {
          numRows = currentRow + 1;
        }
      }
    }
    String color = numRows % 2 == 0 ? "lightskyblue" : "cornflowerblue" ; 
    for (int i=0; i<NUM_COLS; i++) {
      Label label = new Label(String.format("Label [%d, %d]", numRows, i+1)); 
      label.setStyle("-fx-background-color: "+color);
      label.setMaxWidth(Double.POSITIVE_INFINITY);
      GridPane.setHgrow(label, Priority.ALWAYS);
      gridPane.add(label, i, rowIndex);
    }
  }
 
  public static void main(String[] args) { launch(args); }
 
}

