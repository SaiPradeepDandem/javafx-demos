package com.ezest.javafx.uicontrols;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
	 
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleDoubleProperty age;

    public Person(String fName, String lName, String email) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleDoubleProperty();
    }

    public Person(String fName, String lName, String email,Double age) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleDoubleProperty(age);
    }

    public String getFirstName() {
        return firstName.get();
    }
    
    public SimpleStringProperty firstNameProperty(){
    	return firstName;
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public Double getAge() {
        return age.get();
    }
    
    public SimpleDoubleProperty ageProperty(){
    	return age;
    }

    public void setAge(Double age) {
    	this.age.set(age);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String fName) {
        lastName.set(fName);
    }
    public SimpleStringProperty lastNameProperty(){
    	return lastName;
    }

    public String Object() {
        return email.get();
    }

    public void setEmail(String fName) {
        email.set(fName);
    }
    public String getEmail() {
       return email.get();
    }

}
