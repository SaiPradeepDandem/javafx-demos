package com.ezest.javafx.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MemoComponent<Memo> extends VBox{
	private Label titleLabel  = new Label();
	private EMRTableView<Memo> grid;
	private ObservableList<Memo> data = FXCollections.observableArrayList();
	
	public MemoComponent(String title){
		setTitleLabel(title);
		this.data = null;
		setGrid();
	}
	
	public MemoComponent(String title,ObservableList<Memo> data){
		setTitleLabel(title);
		this.data = data;
		setGrid();
	}
	
	private void setTitleLabel(String title){
		titleLabel.setText(title);
		setPadding(new Insets(10, 10, 10, 10));
		getChildren().add(titleLabel);
	}
	
	private void setGrid(){
		grid = new EMRTableView<Memo>(150);
		EMRTableColumn<Memo,String> titleCol = new EMRTableColumn<Memo,String>("Title",350d);
		//titleCol.setProperty("title");
		EMRTableColumn<Memo,String> authorCol = new EMRTableColumn<Memo,String>("Author",100d);
		//authorCol.setProperty("author");
		EMRTableColumn<Memo,String> confidentialityCol = new EMRTableColumn<Memo,String>("Confidentiality",200d);
		//confidentialityCol.setProperty("confidentiality");
		EMRTableColumn<Memo,String> actionCol = new EMRTableColumn<Memo,String>("",50d);
		
		grid.addColumns(titleCol,authorCol,confidentialityCol,actionCol);
		
		if(this.data!=null){
			setData(this.data);
		}
		getChildren().add( grid );
	}
	
	
	public void setData(ObservableList<Memo> dataList){
		grid.setItems(dataList);
	}
	
}
