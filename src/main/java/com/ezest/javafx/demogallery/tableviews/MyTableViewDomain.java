package com.ezest.javafx.demogallery.tableviews;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MyTableViewDomain{
	private SimpleStringProperty name = new SimpleStringProperty();
	private SimpleStringProperty description = new SimpleStringProperty();
	private SimpleIntegerProperty status = new SimpleIntegerProperty();
	
	public MyTableViewDomain(String name, String desc){
		this.name.set(name);
		this.description.set(desc);
		
	}
	public MyTableViewDomain(String name, String desc, int status){
		this.name.set(name);
		this.description.set(desc);
		this.status.set(status);
	}
	
	public MyTableViewDomain(String desc){
		this.description.set(desc);
	}
	public MyTableViewDomain(){}
	
	public String getDescription() {
        return description.get();
    }
    
    public SimpleStringProperty descriptionProperty(){
    	return description;
    }
    
    public String getName() {
        return name.get();
    }
    
    public SimpleStringProperty nameProperty(){
    	return name;
    }
    public int getStatus() {
        return status.get();
    }
    
    public SimpleIntegerProperty statusProperty(){
    	return status;
    }

}
