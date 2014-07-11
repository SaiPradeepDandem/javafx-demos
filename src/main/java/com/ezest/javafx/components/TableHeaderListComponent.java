package com.ezest.javafx.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TableHeaderListComponent<T> extends VBox{

	private Label headerLabel = new Label();
	private ListView<T> listView = new ListView<T>();
	private Callback<T, String> onSelectionCallBack = null;
	
	public TableHeaderListComponent(){
		super();
		headerLabel.getStyleClass().add("tableListHeaderLbl");
		
		StackPane header = new StackPane();
		header.getStyleClass().add("tableListHeader");
		header.setPrefHeight(25);
		header.getChildren().add(headerLabel);
		
		listView.getStyleClass().add("tableHeaderListView");
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
			@Override
			public void changed( ObservableValue<? extends T> paramObservableValue,	T paramT1, T paramT2) {
				if(onSelectionCallBack!=null){
					onSelectionCallBack.call(paramT2);
				}
			}
		});
		
		super.getChildren().addAll(header,listView);
	}
	
	public void setHeaderText(String text){
		headerLabel.setText(text);
	}
	
	public void setItemsInListView(ObservableList<T> list){
		listView.setItems(list);
	}
	
	public ListView<T> getListView(){
		return listView;
	}
	
	public void setOnSelectionCallBack(Callback<T, String> onSelectionCallBack){
		this.onSelectionCallBack = onSelectionCallBack;
	}
	
	public static HBox getTwoColumnSeparatorTemplate(String col1, String col2){
		HBox hb= new HBox();
		hb.setAlignment(Pos.CENTER_LEFT);
		hb.setSpacing(5);
		Separator sp =  new Separator(Orientation.VERTICAL);
		sp.getStyleClass().add("tableHeaderListSeparator");
		
		StackPane stkP = new StackPane();
		stkP.setAlignment(Pos.CENTER);
		stkP.setPrefWidth(20);
		stkP.setPadding(new Insets(0, 0, 0, 5));
		stkP.getChildren().add(new Label(col1));
	
		hb.getChildren().addAll( stkP, sp, new Label(col2));
		return hb;
	}
}
