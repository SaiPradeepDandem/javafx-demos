package com.ezest.javafx.components.autofillcombobox;

public class PostalCode {

	private String name;
	private String code;

	public PostalCode() {
		super();
	}

	public PostalCode(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "PostalCode@[code="+getCode()+", name="+getName()+"]";
	}
}
