package com.ezest.javafx.components.lockingtableview;

import javafx.geometry.Pos;
import javafx.scene.control.LabelBuilder;

public class LockingTableCell<T> extends LockingCell<T>{
	
	/**
	 * Constructor for Header Cell
	 * @param c -  LockingTableColumn<T, ?>
	 */
	public LockingTableCell(LockingTableColumn<T, ?> c){
		super();
		super.getStyleClass().add("locktable-cell");
		super.setAlignment(Pos.CENTER);
		super.minWidthProperty().bind(c.widthProperty());
		super.maxWidthProperty().bind(c.widthProperty());
		if(c.getGraphic()==null){
			String str = c.getColumnTitle();
			label = LabelBuilder.create().text(str).build();
			label.setAlignment(Pos.CENTER);
			getChildren().add(label);
		}else{
			getChildren().add(c.getGraphic());
		}
	}
	
	/**
	 * Constructor for Data Cell
	 * @param c - LockingTableColumn<T, ?>
	 * @param item - T
	 */
	public LockingTableCell(LockingTableColumn<T, ?> c, T item){
		super();
		super.getStyleClass().add("locktable-cell");
		super.alignmentProperty().bind(c.alignmentProperty());
		super.minWidthProperty().bind(c.widthProperty());
		super.maxWidthProperty().bind(c.widthProperty());
		
		boolean emptyFlag = c.getCellObservableValue(item).getValue()==null ? true: false;
		updateItem(c.getCellObservableValue(item).getValue(), emptyFlag);
	}
	
	
	/**
	 * Method to add the style class for the last cell.
	 */
	public void makeLastCell(){
		super.getStyleClass().add("locktable-cell-last");
	}
	
	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		if(label!=null){
			label.setPrefWidth(getWidth()-10);
		}
	}

	
}
