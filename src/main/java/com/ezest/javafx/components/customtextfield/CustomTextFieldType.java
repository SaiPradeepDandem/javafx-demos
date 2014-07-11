package com.ezest.javafx.components.customtextfield;

/**
 * Enum corresponding to the type of the {@link CustomTextField} belongs to.
 * 
 *  @author <a href="sai.dandem@e-zest.in">Sai.Dandem</a>
 *
 */
public enum CustomTextFieldType {
	/**
	 * Specifies the default text field which allows any text.
	 */
	DEFAULT,
	/**
	 * Specifies the text fields which allows alphabets only.
	 */
	ALPHABETS,
	/**
	 * Specifies the text fields which allows small alphabets only.
	 */
	ALPHABETS_SMALL,
	/**
	 * Specifies the text fields which allows capital alphabets only.
	 */
	ALPHABETS_CAPITAL,
	/**
	 * Specifies the text fields which allows integers only (both positive and negative).
	 */
	INTEGER_ONLY,
	/**
	 * Specifies the text fields which allows positive integers only.
	 */
	POSITIVE_INTEGER_ONLY,
	/**
	 * Specifies the text fields which allows number only (both positive and negative integers/double).
	 */
	NUMERIC,
	/**
	 * Specifies the text fields which allows positive number only (integers/double).
	 */
	POSITIVE_NUMERIC_ONLY,
	/**
	 * Specifies the text fields which allows to enter color code only.
	 */
	COLOR_CODE,
	/**
	 * Specifies the time format which allows to enter in HH:MM only.
	 */
	TIME_HHMM_FORMAT
}
