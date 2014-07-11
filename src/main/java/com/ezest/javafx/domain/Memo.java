package com.ezest.javafx.domain;

import javafx.beans.property.SimpleStringProperty;

public class Memo {
	private SimpleStringProperty title = new SimpleStringProperty();
	private SimpleStringProperty author  = new SimpleStringProperty();
	private SimpleStringProperty confidentiality  = new SimpleStringProperty();
	
	public Memo(String title, String author, String confidentiality) {
		super();
		setTitle(title);
		setAuthor(author);
		setConfidentiality(confidentiality);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title.get();
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title.set(title);
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author.get();
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author.set(author);
	}
	/**
	 * @return the confidentiality
	 */
	public String getConfidentiality() {
		return confidentiality.get();
	}
	/**
	 * @param confidentiality the confidentiality to set
	 */
	public void setConfidentiality(String confidentiality) {
		this.confidentiality.set(confidentiality);
	}
	
	public SimpleStringProperty authorProperty(){
		return this.author;
	}
	
	public SimpleStringProperty titleProperty(){
		return this.title;
	}
	
	public SimpleStringProperty confidentialityProperty(){
		return this.confidentiality;
	}
	
	
}
