package com.ezest.javafx.demogallery.controls;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TextField;

public class CustomTextField extends TextField{
	SimpleIntegerProperty maxCharLength = new SimpleIntegerProperty(500);
	SimpleObjectProperty<TextFieldType> type = new SimpleObjectProperty<TextFieldType>(TextFieldType.DEFAULT);
	
	@Override
	public void replaceText(int start, int end, String text) {
		if (matchTest(text)) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (matchTest(text)) {
			super.replaceSelection(text);
		}
	}

	private boolean matchTest(String text) {
		return text.isEmpty()
				|| (text.matches(getType().getFormat()) && (getText() == null || getText()
						.length() < getMaxCharLength()));
	}

	public Integer getMaxCharLength() {
		return maxCharLength.get();
	}

	public void setMaxCharLength(Integer maxCharLength) {
		this.maxCharLength.set(maxCharLength);
	}

	public TextFieldType getType() {
		return type.get();
	}

	public void setType(TextFieldType type) {
		this.type.set(type);
	}
	
	public static enum TextFieldType{
		DEFAULT,
		INTEGER("^[-+]?[0-9]*"), //
		POSITIVE_INTEGER("[0-9\\s]*"), // CORRECT
		DOUBLE("\\d+\\.\\d+"),
		POSITIVE_DOUBLE,
		ALPHABET("[a-zA-z\\s]*"),// CORRECT
		ALPHA_NUMBERIC("[a-zA-z0-9\\s]*");// CORRECT
		
		String format ;
		TextFieldType(String format){
			this.format =format;
		}
		TextFieldType(){ }
		public String getFormat(){
			return format;
		}
	}
}


