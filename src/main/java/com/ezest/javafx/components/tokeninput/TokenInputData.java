package com.ezest.javafx.components.tokeninput;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TokenInputData {

	private SimpleStringProperty title = new SimpleStringProperty();
	private SimpleStringProperty description = new SimpleStringProperty();
	private SimpleIntegerProperty year = new SimpleIntegerProperty();
	
	public TokenInputData(String title,	String description, Integer year) {
		super();
		this.title.set(title);
		this.description.set(description);
		this.year.set(year);
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description .set(description);
	}

	public Integer getYear() {
		return year.get();
	}

	public void setYear(Integer year) {
		this.year.set(year);
	}
	
	
}
