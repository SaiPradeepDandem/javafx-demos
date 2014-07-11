package com.ezest.javafx.components.freetextfield;

import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class FreeTextField extends TextField{
	
	private double CAPS_SPECITAL_LETTERS = 8.37;
	private double NUMERIC_LETTERS = 7.69;
	public FreeTextField(){
		super();
		setPrefWidth(100);
		
		textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0,	String arg1, String txt) {
				double len=0;
				char[] arr = txt.toCharArray();
				for (char c : arr) {
					if(c>='a' && c<='z'){
						len = len + SMALL_LETTERS_MAP.get(c+"");
					}else if(c>='0' && c<='9'){
						len = len + NUMERIC_LETTERS;
					}else{
						len = len + CAPS_SPECITAL_LETTERS;
					}
				}
				len = (len>100)? (len>450 ? 450 :len): 100;
				System.out.println("Len :"+len);
				FreeTextField.this.setPrefWidth(len);
			}
		});
	}
	
	private static HashMap<String, Double> SMALL_LETTERS_MAP = new HashMap<String, Double>();
	
	static{
		SMALL_LETTERS_MAP.put("a", 7.14);
		SMALL_LETTERS_MAP.put("b", 8.33);
		SMALL_LETTERS_MAP.put("c", 6.66);
		SMALL_LETTERS_MAP.put("d", 8.33);
		SMALL_LETTERS_MAP.put("e", 7.14);
		SMALL_LETTERS_MAP.put("f", 4.34);
		SMALL_LETTERS_MAP.put("g", 8.33);
		SMALL_LETTERS_MAP.put("h", 8.33);
		SMALL_LETTERS_MAP.put("i", 3.33);
		SMALL_LETTERS_MAP.put("j", 3.33);
		SMALL_LETTERS_MAP.put("k", 7.14);
		SMALL_LETTERS_MAP.put("l", 3.33);
		SMALL_LETTERS_MAP.put("m", 12.5);
		SMALL_LETTERS_MAP.put("n", 8.33);
		SMALL_LETTERS_MAP.put("o", 8.33);
		SMALL_LETTERS_MAP.put("p", 8.33);
		SMALL_LETTERS_MAP.put("q", 8.33);
		SMALL_LETTERS_MAP.put("r", 4.76);
		SMALL_LETTERS_MAP.put("s", 5.88);
		SMALL_LETTERS_MAP.put("t", 4.76);
		SMALL_LETTERS_MAP.put("u", 8.33);
		SMALL_LETTERS_MAP.put("v", 6.66);
		SMALL_LETTERS_MAP.put("w", 10.0);
		SMALL_LETTERS_MAP.put("x", 6.66);
		SMALL_LETTERS_MAP.put("y", 6.66);
		SMALL_LETTERS_MAP.put("z", 6.25);
	}
}
