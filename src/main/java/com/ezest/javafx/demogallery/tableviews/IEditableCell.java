package com.ezest.javafx.demogallery.tableviews;

/**
 * Provides an interface to editable graphical JavaFX controls.
 * @author <a href="mailto:dennis.wagelaar@healthconnect.be">Dennis Wagelaar</a>
 *
 */
public interface IEditableCell {

	/**
	 * Call this method to transition from an editing state into a non-editing
	 * state, and in the process saving any pending user input.
	 */
	public void commitEdit();

}
