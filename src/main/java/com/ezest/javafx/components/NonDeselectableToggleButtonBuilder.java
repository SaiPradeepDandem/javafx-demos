package com.ezest.javafx.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPaneBuilder;

/**
 * Builder class for  NonDeselectableToggleButton control.
 * 
 * @author <a href="sai.dandem@e-zest.in">Sai Dandem</a>
 *
 */
public class NonDeselectableToggleButtonBuilder<B extends NonDeselectableToggleButtonBuilder<B>> extends StackPaneBuilder<B> {
	/**
	 * Creates a new instance of {@link NonDeselectableToggleButtonBuilder}.
	 * 
	 * @return a new instance of {@link NonDeselectableToggleButtonBuilder}
	 */
	@SuppressWarnings("rawtypes")
	public static NonDeselectableToggleButtonBuilder<?> create() {
		return new NonDeselectableToggleButtonBuilder();
	}

	private String text;
	private ToggleGroup toggleGroup;
	private String[] styleClasses;
	private EventHandler<ActionEvent> actionEvent;
	
	/**
	 * {@inheritDoc}
	 */
	public void applyTo(NonDeselectableToggleButton x) {
		super.applyTo(x);
		x.setText(text);
		x.setToggleGroup(toggleGroup);
		x.setStyleClass(styleClasses);
		x.setOnAction(actionEvent);
	}
	
	/**
	 * Sets the text property of the NonDeselectableToggleButton.
	 * 
	 * @param text
	 *            the text to set
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public B text(String text) {
		this.text = text;
		return (B) this;
	}

	/**
	 * Sets the style classes of the NonDeselectableToggleButton.
	 * 
	 * @param styleClasses
	 *            Array of style classes.
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public B styleClass(String... styleClasses){
		this.styleClasses = styleClasses;
		return (B) this;
	}
	
	/**
	 * Sets the ToggleGroup property of the NonDeselectableToggleButton.
	 * 
	 * @param toggleGroup
	 *            the toggleGroup to set
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public B toggleGroup(ToggleGroup toggleGroup) {
		this.toggleGroup = toggleGroup;
		return (B) this;
	}

	/**
	 * Sets the action EventHandler property of the NonDeselectableToggleButton.
	 * 
	 * @param toggleGroup
	 *            the toggleGroup to set
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public B onAction(EventHandler<ActionEvent> actionEvent) {
		this.actionEvent = actionEvent;
		return (B) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NonDeselectableToggleButton build() {
		final NonDeselectableToggleButton button = new NonDeselectableToggleButton();
		applyTo(button);
		return button;
	}
}
