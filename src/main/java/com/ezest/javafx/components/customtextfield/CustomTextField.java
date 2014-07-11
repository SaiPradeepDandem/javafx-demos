package com.ezest.javafx.components.customtextfield;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;

/**
 * Custom TextField control which allows the user to input specific type of key inputs only based on the provided
 * {@link CustomTextFieldType}.
 * 
 * @author <a href="sai.dandem@e-zest.in">Sai.Dandem</a>
 * 
 */
public class CustomTextField extends TextField {

	private SimpleObjectProperty<CustomTextFieldType> type;
	private SimpleIntegerProperty restrictSize;
	private SimpleStringProperty regex;

	/**
	 * Creates the default {@link CustomTextField} control, which allows any key input.
	 */
	public CustomTextField() {
		this(CustomTextFieldType.DEFAULT);
	}

	/**
	 * Creates the default {@link CustomTextField} control, which allows any key input with the provide default text.
	 * 
	 * @param text
	 *            Default text in the control.
	 */
	public CustomTextField(String text) {
		super(text);
		setType(CustomTextFieldType.DEFAULT);
	}

	/**
	 * Creates the {@link CustomTextField} control, which allows key input based on the provided type.
	 * 
	 * @param type
	 *            Type of the text field.
	 */
	public CustomTextField(CustomTextFieldType type) {
		super();
		setType(type);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void replaceText(int start, int end, String text) {
		// If the replaced text would end up being invalid, then simply ignore this call!
		if (accept(text)) {
			super.replaceText(start, end, text.trim());
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void replaceSelection(String text) {
		if (accept(text)) {
			super.replaceSelection(text);
		}
	}

	/**
	 * Performs check whether the entered key input is valid or not based on the given CustomTextFieldType.
	 * 
	 * @param text
	 *            Key input entered in the text field.
	 * @return True if the entered key matches to the type, else false.
	 */
	private boolean accept(String text) {
		text = text.trim();
		if (text.length() == 0)
			return true;
		if (getSelectedText().isEmpty() && getRestrictSize() > 0) {
			if (getText().length() > (getRestrictSize() - 1))
				return false;
		}
		String newTxt;
		switch (getType()) {
		case ALPHABETS:
			if (!text.matches("[a-zA-Z]"))
				return false;
			break;
		case ALPHABETS_SMALL:
			if (!text.matches("[a-z]"))
				return false;
			break;
		case ALPHABETS_CAPITAL:
			if (!text.matches("[A-Z]"))
				return false;
			break;
		case INTEGER_ONLY:
			if (text.equals("+") || text.equals("-")) {
				newTxt = text + (getText() != null ? getText() : "");
			} else {
				newTxt = (getText() != null ? getText() : "") + text;
			}
			if (!newTxt.matches("^[-+]?[0-9]*"))
				return false;
			break;
		case POSITIVE_INTEGER_ONLY:
			if (!text.matches("[0-9]"))
				return false;
			break;
		case NUMERIC:
			if (text.equals("+") || text.equals("-")) {
				newTxt = text + (getText() != null ? getText() : "");
			} else {
				newTxt = (getText() != null ? getText() : "") + text;
			}
			if (!newTxt.matches("^[-+]?[0-9]*\\.?[0-9]*([eE][-+]?[0-9]+)?$"))
				return false;
			break;
		case POSITIVE_NUMERIC_ONLY:
			newTxt = (getText() != null ? getText() : "") + text;
			if (!newTxt.matches("[0-9]*\\.?[0-9]*([eE][-+]?[0-9]+)?$"))
				return false;
			break;
		case COLOR_CODE:
			if (getText() != null) {
				newTxt = (getText() != null ? getText() : "") + text;
				// If the new text does not satisfy the color code characters.
				if (!newTxt.matches("^[#]?[a-fA-F0-9]*")) {
					return false;
				}

				// If the new text length is exceeded.
				if ((newTxt.indexOf("#") == 0) && (newTxt.length() > 7)) {
					return false;
				} else if ((newTxt.indexOf("#") == -1) && (newTxt.length() > 6)) {
					return false;
				}
			}
			break;
		case TIME_HHMM_FORMAT:
			newTxt = (getText() != null ? getText() : "") + text;
			if (!newTxt
					.matches("(([0-9]|0[0-9]|1[0-9]|2[0-3]))|(([0-9]|0[0-9]|1[0-9]|2[0-3])[:])|(^[0-2]?[0-3]:[0-5]$)|(^[0-2]?[0-3]:[0-5][0-9]$)"))
				return false;
			break;
		default:
			// The default will check for the regular expression provided if any.
			if (!getRegex().isEmpty()) {
				if (!getText().isEmpty()) {
					text = getText() + text;
				}
				if (!text.matches(getRegex()))
					return false;
			}
		}
		return true;
	}

	/**
	 * Returns the type of the text field.
	 * 
	 * @return Value of <code>type</code> property.
	 */
	public CustomTextFieldType getType() {
		return typeProperty().get();
	}

	/**
	 * Returns the observable value of the type field.
	 * 
	 * @return <code>type</code> property.
	 */
	public SimpleObjectProperty<CustomTextFieldType> typeProperty() {
		if (type == null) {
			type = new SimpleObjectProperty<CustomTextFieldType>(CustomTextFieldType.DEFAULT);
		}
		return type;
	}

	/**
	 * Sets the type of the text field.
	 * 
	 * @param type
	 *            {@link CustomTextFieldType} of the text field.
	 */
	public void setType(CustomTextFieldType type) {
		typeProperty().set(type == null ? CustomTextFieldType.DEFAULT : type);
	}

	/**
	 * Returns the maximum number of characters that can be entered in {@link CustomTextField}.
	 * 
	 * @return Maximum no of characters in the {@link CustomTextField}.
	 */
	public int getRestrictSize() {
		return restrictSizeProperty().get();
	}

	/**
	 * Returns the observable property of <code>restrictSize</code>.
	 * 
	 * @return <code>restrictSize</code> property.
	 */
	public SimpleIntegerProperty restrictSizeProperty() {
		if (restrictSize == null) {
			restrictSize = new SimpleIntegerProperty();
		}
		return restrictSize;
	}

	/**
	 * Sets the maximum no characters that can be entered in the text field.
	 * 
	 * @param size
	 *            No of characters.
	 */
	public void setRestrictSize(int size) {
		this.restrictSizeProperty().set(size);
	}

	/**
	 * Returns the regular expression that is set for the text field.
	 * 
	 * @return Regular expression string.
	 */
	public String getRegex() {
		return regexProperty().get();
	}

	/**
	 * Returns the observable property of <code>regex</code>.
	 * 
	 * @return <code>regex</code> property.
	 */
	public SimpleStringProperty regexProperty() {
		if (regex == null) {
			regex = new SimpleStringProperty("");
		}
		return regex;
	}

	/**
	 * Sets the regular expression that is used to validate against the text entered in the text field.
	 * 
	 * @param regex
	 *            Regular expression string.
	 */
	public void setRegex(String regex) {
		this.regexProperty().set(regex == null ? "" : regex);
	}
}
