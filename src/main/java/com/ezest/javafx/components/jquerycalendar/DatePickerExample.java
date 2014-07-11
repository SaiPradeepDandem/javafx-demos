package com.ezest.javafx.components.jquerycalendar;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Demonstrates use of a JQueryUI based DatePicker Component to pick a date. */
public class DatePickerExample extends Application {
  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage stage) {
    stage.setTitle("jQueryUI based DatePicker Example");
    
    // create the controls.
    final Label selectedDate = new Label();
    final DatePicker datePicker = new DatePicker(DatePicker.Theme.redmond, "minDate: '-0d', maxDate: '+0m +2w'");
    selectedDate.textProperty().bind(
      new SimpleStringProperty("Selected date: ").concat(datePicker.dateStringProperty())
    );

    // layout the scene.
    VBox layout = new VBox(20);
    layout.setStyle("-fx-padding: 20; -fx-font-size: 20; -fx-background-color: cornsilk;");
    layout.getChildren().addAll(selectedDate, datePicker);
    stage.setScene(new Scene(layout));
    stage.show();
  }
}