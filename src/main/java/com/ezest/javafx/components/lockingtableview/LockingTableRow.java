package com.ezest.javafx.components.lockingtableview;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class LockingTableRow extends StackPane{
	private HBox hb;
	
	/**
	 * Constructor for Data row
	 * @param rowCount
	 */
	public LockingTableRow(int rowCount){
		super();
		super.getStyleClass().add("locktable-row-cell");
		if((rowCount+1)%2==0){
			super.getStyleClass().add("locktable-row-cell-odd");
		}
		super.setMinHeight(24);
		hb = new HBox();
		getChildren().add(hb);
	}
	
	/**
	 * Constructor for Header row
	 */
	public LockingTableRow() {
		super();
		super.getStyleClass().add("locktable-headerrow-cell");
		super.setMinHeight(25);
		hb = new HBox();
		getChildren().add(hb);
	}

	public HBox getRow(){
		return hb;
	}
	
	public ObservableList<Node> getCells(){
		return hb.getChildren();
	}
}
