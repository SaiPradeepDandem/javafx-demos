package com.ezest.javafx.domain;

import javafx.beans.property.SimpleStringProperty;

public class MyDomain{
	private SimpleStringProperty name = new SimpleStringProperty();
	private SimpleStringProperty description = new SimpleStringProperty();
	public MyDomain(String name, String desc){
		this.name.set(name);
		this.description.set(desc);
		
	}
	public MyDomain(String desc){
		this.description.set(desc);
	}
	public MyDomain(){}
	
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

    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return getDescription();
    }
}

