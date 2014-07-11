package com.ezest.javafx.components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EMRTableView<T> extends TableView<T> {
	
	public EMRTableView(){
		super();
	}
	
	public EMRTableView(double height){
		super();
		setPrefHeight(height);
	}
	
	public void addColumns(TableColumn...columns){
		getColumns().addAll(columns);
	}

}
