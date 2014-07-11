package com.ezest.javafx.uicontrols;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;

public class CheckBoxGroup {

	public static Node getCheckBox(){
		
		//A checkbox without a caption
		CheckBox cb1 = new CheckBox();
		
		//A checkbox with a string caption
		CheckBox cb2 = new CheckBox("Second");

		cb1.setText("First");
		cb1.setSelected(true);
		cb1.setStyle(
			    "-fx-border-color: lightblue; "
			    + "-fx-font-size: 20;"
			    + "-fx-border-insets: -5; "
			    + "-fx-border-radius: 5;"
			    + "-fx-border-style: dotted;"
			    + "-fx-border-width: 2;"
			);
		return cb1;
	}
	
	
}
