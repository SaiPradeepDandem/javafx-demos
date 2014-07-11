package com.ezest.javafx.colorfulcircles;

import java.util.ArrayList;
import java.util.List;

public class Check {
	public static void main(String[] args) {
		List<String> a = new ArrayList<String>();
		a.add("a");
		a.add("b");
		a.add("c");
		
		List<String> b = new ArrayList<String>();
		b.add("d");
		b.add("b");
		b.add("f");
		
		for (String string1 : a) {
			for (String string2 : b) {
				if(string1.equals(string2)){
					a.remove(string1);
				}
			}
		}
		for (String string1 : a) {
			System.out.println(string1);
		}
	}
	
}
