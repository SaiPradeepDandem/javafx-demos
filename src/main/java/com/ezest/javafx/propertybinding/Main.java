package com.ezest.javafx.propertybinding;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bill bill = new Bill();
		bill.amountDueProperty().addListener( new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue var, Object oldValue, Object newValue) {
				System.out.println("Bill is changed arg0 : "+var);
				System.out.println("Bill is changed arg1 : "+oldValue);
				System.out.println("Bill is changed arg2 : "+newValue);
			}
		});
		
		bill.setAmountDue(56);
		
		bill.setAmountDue(66);
		
		IntegerProperty num1 = new SimpleIntegerProperty(23);
		IntegerProperty num2 = new SimpleIntegerProperty(26);
		NumberBinding sum = num1.add(num2);
		System.out.println("Sum : "+sum.getValue());
		
		num2.set(56);
		System.out.println("Sum : "+sum.getValue());
		
	}

}
