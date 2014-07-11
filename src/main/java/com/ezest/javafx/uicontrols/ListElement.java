package com.ezest.javafx.uicontrols;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;

public class ListElement {

	public static Node getSimpleList(){
		ListView<String> list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList("Single","Double","Suite","Family Apt1","Family Apt2","Family Apt3","Family Apt4","Family Apt5");
		list.setItems(items);
		list.setPrefWidth(100);
		list.setPrefHeight(70);
		return list;
	}
	
	public static Node getHSimpleList(){
		ListView<String> list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList("Single","Double","Suite","Family Apt1","Family Apt2","Family Apt3","Family Apt4","Family Apt5");
		list.setItems(items);
		list.setPrefWidth(100);
		list.setPrefHeight(70);
		list.setOrientation(Orientation.HORIZONTAL);
		return list;
		
	}
}
