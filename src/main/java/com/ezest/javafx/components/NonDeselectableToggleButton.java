package com.ezest.javafx.components;

import java.util.Arrays;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

/**
 * ToggleButton control which cannot be deselected by clicking on the same button. The deselection can happen through the other 
 * {@link ToggleButton}s which are in the same group.
 * 
 * @author <a href="sai.dandem@e-zest.in">Sai Dandem</a>
 *
 */
public class NonDeselectableToggleButton extends StackPane{
	private final ToggleButton toggleButton;
	private final StackPane mask = new StackPane();
	private final NonDeselectableToggleButton this$;

	public NonDeselectableToggleButton() {
		super();
		this$ = this;
		this.toggleButton = new ToggleButton();
		configure();
	}

	public NonDeselectableToggleButton(String text) {
		super();
		this$ = this;
		this.toggleButton = new ToggleButton(text);
		configure();
	}

	private void configure(){
		mask.setVisible(false);
		this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(toggleButton,mask);
		
		// Adding the mask to the control based on the selection property of the ToggleButton.
		toggleButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean selected) {
				if(selected){
					mask.setVisible(true);
				}else{
					mask.setVisible(false);
				}
			}
		});
	}
	
	/**
	 * Returns the associated {@link ToggleButton} in the control.
	 * @return ToggleButton
	 */
	public ToggleButton getToggleButton() {
		return toggleButton;
	}
	
	/**
	 * Sets the provided {@link ToggleGroup} to the {@link ToggleButton} in the control.
	 * @param group 
	 *          The {@link ToggleGroup} that need to be set.
	 */
	public void setToggleGroup(ToggleGroup group){
		this.toggleButton.setToggleGroup(group);
	}
	
	/**
	 * Sets the provided string to the {@link ToggleButton} in the control.
	 * @param text 
	 *          The text that need to be set.
	 */
	public void setText(String text){
		this.toggleButton.setText(text);
	}
	
	/**
	 * Sets the given array of style classes to the {@link ToggleButton} in the control.
	 * @param styleClass
	 *            Array of style classes.
	 */
	public void setStyleClass(String... styleClass){
		this.toggleButton.getStyleClass().addAll(Arrays.asList(styleClass));
	}
	
	/**
	 * Sets the provided action event to the {@link ToggleButton} in the control.
	 * @param event
	 *           EventHandler for the toggle button.
	 */
	public void setOnAction(EventHandler<ActionEvent> event){
		this.toggleButton.setOnAction(event);
	}
}
