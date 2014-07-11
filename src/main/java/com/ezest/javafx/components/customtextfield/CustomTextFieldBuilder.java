/**
 * (C) 2013 HealthConnect CVBA. All rights reserved.
 */
package com.ezest.javafx.components.customtextfield;

import javafx.scene.control.TextFieldBuilder;

/**
 * Builder class for {@link CustomTextField} control.
 * 
 * @author <a href="sai.dandem@e-zest.in">Sai.Dandem</a>
 * 
 */
public class CustomTextFieldBuilder<B extends CustomTextFieldBuilder<B>> extends TextFieldBuilder<B> {
	private CustomTextFieldType type;
	private int restrictSize;
	private String regex;

	/**
	 * Creates a new instance of {@link CustomTextFieldBuilder}.
	 * 
	 * @return a new instance of {@link CustomTextFieldBuilder}
	 */
	@SuppressWarnings({ "rawtypes" })
	public static CustomTextFieldBuilder<?> create() {
		return new CustomTextFieldBuilder();
	}

	/**
	 * Sets the type of custom text field.
	 * 
	 * @param type
	 *            - {@link CustomTextFieldType} of the custom text field..
	 * @return {@link CustomTextFieldBuilder}
	 */
	public CustomTextFieldBuilder<B> type(CustomTextFieldType type) {
		this.type = type;
		return this;
	}

	/**
	 * Sets the maximum number of characters that can be entered in the {@link CustomTextField}.
	 * 
	 * @param restrictSize
	 *            - Maximum no of characters.
	 * @return {@link CustomTextFieldBuilder}
	 */
	public CustomTextFieldBuilder<B> restrictSize(int restrictSize) {
		this.restrictSize = restrictSize;
		return this;
	}

	/**
	 * Sets the regular expression for the {@link CustomTextField}.
	 * 
	 * @param regex
	 *            - Regular expression to which the entered text need to be validated.
	 * @return {@link CustomTextFieldBuilder}
	 */
	public CustomTextFieldBuilder<B> regex(String regex) {
		this.regex = regex;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CustomTextField build() {
		CustomTextField localTextField = new CustomTextField();
		applyTo(localTextField);
		localTextField.setType(this.type);
		localTextField.setRestrictSize(this.restrictSize);
		localTextField.setRegex(this.regex);
		return localTextField;
	}

}
