package com.ezest.javafx.components.nullcellediting;

import javafx.beans.property.SimpleStringProperty;

public class Person {
 
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
 
    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }
 
    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String firstName) { this.firstName.set(firstName); }
    public SimpleStringProperty firstNameProperty() { return firstName; }
 
    public String getLastName() { return lastName.get(); }
    public void setLastName(String lastName) { this.lastName.set(lastName); }
    public SimpleStringProperty lastNameProperty() { return lastName; }
}
