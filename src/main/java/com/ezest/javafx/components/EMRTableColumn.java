package com.ezest.javafx.components;

import javafx.scene.control.TableColumn;

public class EMRTableColumn<S,T> extends TableColumn<S,T>{

	public EMRTableColumn(String s) {
		super(s);
	}
	
	public EMRTableColumn(String s,Double width) {
		super(s);
		setPrefWidth(width);
	}

}
