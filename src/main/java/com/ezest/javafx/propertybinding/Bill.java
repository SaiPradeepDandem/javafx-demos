package com.ezest.javafx.propertybinding;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Bill {
	private SimpleDoubleProperty amountDue = new SimpleDoubleProperty();

	/**
	 * @return the amountDue
	 */
	public double getAmountDue() {
		return amountDue.get();
	}

	/**
	 * @param amountDue the amountDue to set
	 */
	public void setAmountDue(double amountDue) {
		this.amountDue.set(amountDue);
	}
	
	// Define a getter for the property itself
    public DoubleProperty amountDueProperty() {
    	return amountDue;
    }
}
