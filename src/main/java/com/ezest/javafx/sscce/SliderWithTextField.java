package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SliderWithTextField extends Application  {

  public static final String SLIDER_TOOLTIP =
    "A slider ranging from 0 to 100 whose values are bound to the fields to the left.";
  public static final String LABEL_TOOLTIP  =
    "Click on me to edit the sliders value.\n" +
    "The slider will be updated when you hit the Enter key to commit the edit.\n" +
    "If you do not enter a value in the sliders range,\n" +
    "then the entered value will be ignored.\n" +
    "You can hit the Escape key or move the slider to cancel an edit.";
  public static final String EDIT_FIELD_TOOLTIP =
    "Click on me to edit the slider's value,\n" +
    "but you will only be able to enter numeric values within the slider range.\n" +
    "The slider will be updated as you type\n" +
    "and there is no need to commit the edit with an enter key.";

  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage stage) throws Exception {
    // create a slider.
    final Slider slider = new Slider(0, 100, 50);
    slider.setTooltip(new Tooltip(SLIDER_TOOLTIP));

    // bind a plain text label to the slider value.
    final Label text = new Label();
    text.setTooltip(new Tooltip(LABEL_TOOLTIP));
    text.setText(Math.round(slider.getValue()) + "");
    slider.valueProperty().addListener(new ChangeListener<Number>() {
      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        if (newValue == null) {
          text.setText("");
          return;
        }
        text.setText(Math.round(newValue.intValue()) + "");
      }
    });
    text.setPrefWidth(50);

    // allow the label to be clicked on to display an editable text field and have a slider movement cancel any current edit.
    text.setOnMouseClicked(newLabelEditHandler(text, slider));
    slider.valueProperty().addListener(new ChangeListener<Number>() {
      @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
        text.setContentDisplay(ContentDisplay.TEXT_ONLY);
      }
    });

    // bind the slider bidirectionally to an editable text field restricted to the slider range.
    final IntField intField = new IntField(0, 100, 50);
    intField.setTooltip(new Tooltip(EDIT_FIELD_TOOLTIP));
    intField.valueProperty().bindBidirectional(slider.valueProperty());
    intField.setPrefWidth(50);

    // layout the scene.
    HBox controls = new HBox(10);
    controls.getChildren().addAll(text, intField, slider);
    controls.setStyle("-fx-background-color: cornsilk; -fx-padding:10; -fx-font-size: 16; -fx-alignment: baseline-left;");
    final Scene scene = new Scene(controls);
    stage.setScene(scene);
    stage.show();
  }

  // helper eventhandler that allows a text label to be edited within the range of a slider.
  private EventHandler<MouseEvent> newLabelEditHandler(final Label text, final Slider slider) {
    return new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        final TextField editField = new TextField(text.getText());
        text.setGraphic(editField);
        text.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        editField.setTranslateY(-3); // adjustment hack to get alignment right.
        editField.requestFocus();
        editField.selectAll(); // hmm there is a weird bug in javafx here, the text is not selected,
                               // but if I focus on another window by clicking only on title bars,
                               // then back to the javafx app, the text is magically selected.

        editField.setOnKeyReleased(new EventHandler<KeyEvent>() {
          @Override public void handle(KeyEvent t) {
            if (t.getCode() == KeyCode.ENTER) {
              text.setContentDisplay(ContentDisplay.TEXT_ONLY);

              // field is empty, cancel the edit.
              if (editField.getText() == null || editField.getText().equals("")) {
                return;
              }

              try {
                double editedValue = Double.parseDouble(editField.getText());
                if (slider.getMin() <= editedValue && editedValue <= slider.getMax()) {
                  // edited value was within in the valid slider range, perform the edit.
                  slider.setValue(Integer.parseInt(editField.getText()));
                  text.setText(editField.getText());
                }
              } catch (NumberFormatException e) { // a valid numeric value was not entered,
                text.setContentDisplay(ContentDisplay.TEXT_ONLY);
              }
            } else if (t.getCode() == KeyCode.ESCAPE) {
              text.setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
          }
        });
      }
    };
  }

  // helper text field subclass which restricts text input to a given range of natural int numbers
  // and exposes the current numeric int value of the edit box as a value property.
  class IntField extends TextField {
    final private IntegerProperty value;
    final private int minValue;
    final private int maxValue;

    // expose an integer value property for the text field.
    public int  getValue()                 { return value.getValue(); }
    public void setValue(int newValue)     { value.setValue(newValue); }
    public IntegerProperty valueProperty() { return value; }
    
    IntField(int minValue, int maxValue, int initialValue) {
      if (minValue > maxValue) 
        throw new IllegalArgumentException(
          "IntField min value " + minValue + " greater than max value " + maxValue
        );
      if (maxValue < minValue) 
        throw new IllegalArgumentException(
          "IntField max value " + minValue + " less than min value " + maxValue
        );
      if (!((minValue <= initialValue) && (initialValue <= maxValue))) 
        throw new IllegalArgumentException(
          "IntField initialValue " + initialValue + " not between " + minValue + " and " + maxValue
        );

      // initialize the field values.
      this.minValue = minValue;
      this.maxValue = maxValue;
      value = new SimpleIntegerProperty(initialValue);
      setText(initialValue + "");

      final IntField intField = this;

      // make sure the value property is clamped to the required range
      // and update the field's text to be in sync with the value.
      value.addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
          if (newValue == null) {
            intField.setText("");
          } else {
            if (newValue.intValue() < intField.minValue) {
              value.setValue(intField.minValue);
              return;
            }

            if (newValue.intValue() > intField.maxValue) {
              value.setValue(intField.maxValue);
              return;
            }

            if (newValue.intValue() == 0 && (textProperty().get() == null || "".equals(textProperty().get()))) {
              // no action required, text property is already blank, we don't need to set it to 0.
            } else {
              intField.setText(newValue.toString());
            }
          }
        }
      });

      // restrict key input to numerals.
      this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
        @Override public void handle(KeyEvent keyEvent) {
          if (!"0123456789".contains(keyEvent.getCharacter())) {
            keyEvent.consume();
          }
        }
      });
      
      // ensure any entered values lie inside the required range.
      this.textProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
          if (newValue == null || "".equals(newValue)) {
            value.setValue(0);
            return;
          }

          final int intValue = Integer.parseInt(newValue);

          if (intField.minValue > intValue || intValue > intField.maxValue) {
            textProperty().setValue(oldValue);
          }
          
          value.set(Integer.parseInt(textProperty().get()));
        }
      });
    }
  }
}

