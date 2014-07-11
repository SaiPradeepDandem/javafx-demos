package com.ezest.javafx.uicontrols;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;

public class ChoiceBoxElement {

	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static Node getNode(){
		
		final ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("New Document", "Open ", 
			    new Separator(), "Save", "Save as"));
		cb.setTooltip(new Tooltip("Select the language"));
		cb.getStyleClass().add("my-choice-box");
		
		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue arg0, Number arg1, Number arg2) {
				if(arg2.intValue()==1){
					cb.getSelectionModel().clearSelection();
				}
			}
		});
		return cb;
	}
}
