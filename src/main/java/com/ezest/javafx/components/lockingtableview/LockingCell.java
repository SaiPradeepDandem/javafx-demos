package com.ezest.javafx.components.lockingtableview;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.StackPane;

public class LockingCell<T> extends StackPane{
	protected Label label ;
	
	public LockingCell(){
		super();
	}
	
	protected Label getLabel() {
		return label;
	}

	protected void setLabel(Label label) {
		this.label = label;
	}

	protected void setText(Object item){
		getChildren().clear();
		if(item!=null){
			setLabel(LabelBuilder.create().text(item.toString()).build());
			getChildren().add(getLabel());
		}
	}
	
	protected void setGraphic(Node node){
		getChildren().clear();
		if(node!=null){
			getChildren().add(node);
		}
	}
	protected void updateItem(Object item, boolean paramBoolean) {
		setText(item);
	}
	 
	
}
