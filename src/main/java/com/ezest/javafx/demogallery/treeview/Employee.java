package com.ezest.javafx.demogallery.treeview;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
	private final SimpleLongProperty id;
	private final SimpleStringProperty name;

	public Employee(long id, String name) {
		this.id = new SimpleLongProperty(id);
		this.name = new SimpleStringProperty(name);
	}

	public long getId() {
		return id.get();
	}

	public void setId(long id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String fName) {
		name.set(fName);
	}
	@Override
	public String toString() {
		return getName();
	}
}
