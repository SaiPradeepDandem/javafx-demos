package com.ezest.javafx.demogallery.webview;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPaneBuilder;

public class SliderTextField extends HBox{
	private SimpleIntegerProperty value = new SimpleIntegerProperty();
	
	private final int startValue;
	private final int endValue;
	private SimpleStringProperty lbl = new SimpleStringProperty();
	private SimpleBooleanProperty disabled = new SimpleBooleanProperty();
	
	public SliderTextField(int startValue, int endValue, int pos){
		super();
		this.startValue = startValue;
		this.endValue = endValue;
		value.set(pos);
		setMinHeight(26);
		configure();
	}
	
	public SliderTextField(int startValue, int endValue, int pos, String label){
		this(startValue, endValue, pos);
		lbl.set(label);
	}
	
	private void configure(){
		super.setMaxHeight(24);
		super.setMinWidth(200);
		super.setAlignment(Pos.CENTER_LEFT);
		super.setSpacing(5);
		
		Slider slider = new Slider(startValue, endValue, value.get());
		slider.disableProperty().bind(disabled);
		//slider.setShowTickLabels(true);
		
		IntField intField = new IntField(startValue, endValue, value.get());
		intField.disableProperty().bind(disabled);
		intField.valueProperty().bindBidirectional(slider.valueProperty());
		value.bindBidirectional(intField.valueProperty());
		
	    intField.setPrefWidth(50);
	    
	    Label label = new Label();
	    label.setStyle("-fx-font-style:italic;");
	    label.textProperty().bind(lbl);
	    
	    getChildren().addAll(intField, StackPaneBuilder.create().children(label).prefWidth(30).alignment(Pos.CENTER_LEFT).build(), slider);
	}
	
	public SimpleIntegerProperty valueProperty(){
		return this.value;
	}
	
	public SimpleBooleanProperty sliderDisableProperty(){
		return this.disabled;
	}
	
	/**
	 * Integer Field for the TextField.
	 * @author Sai.Dandem
	 *
	 */
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
