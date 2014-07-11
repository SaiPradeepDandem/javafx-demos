package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.ezest.javafx.components.CustomTableView;
import com.ezest.javafx.components.CustomTableView.CustomTableColumn;

public class CustomTableViewDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configureTable();
	}

	private void configureTable() {
		
		final ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("Apple","This is a fruit.","Red"),
				 new MyDomain("Orange","This is also a fruit.","Orange"),
				 new MyDomain("Potato","This is a vegetable.","Brown")
				 );
		
		CustomTableView<MyDomain> table = new CustomTableView<MyDomain>();
		
		CustomTableColumn<MyDomain,String> titleColumn = new CustomTableColumn<MyDomain,String>("Title");
		titleColumn.setPercentWidth(25);
		titleColumn.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("name"));
		
			
		CustomTableColumn<MyDomain,String> descCol = new CustomTableColumn<MyDomain,String>("Description");
		descCol.setPercentWidth(55);
		descCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
		
		CustomTableColumn<MyDomain,String> colorCol = new CustomTableColumn<MyDomain,String>("Color");
		colorCol.setPercentWidth(20);
		colorCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("color"));
		
		table.getTableView().getColumns().addAll(titleColumn,descCol,colorCol);
		table.getTableView().setItems(data);
		
		root.getChildren().add(table);
	}

	private void configureStage(){
		stage.setTitle("TableAutoSizeDemo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(700);
	    stage.setHeight(400);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
	}

	public class MyDomain{
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();
		private SimpleStringProperty color = new SimpleStringProperty();
		public MyDomain(String name, String desc,String color){
			this.name.set(name);
			this.description.set(desc);
			this.color.set(color);
			
		}
		public MyDomain(String desc){
			this.description.set(desc);
		}
		public MyDomain(){}
		
		public String getDescription() {
	        return description.get();
	    }
	    
	    public SimpleStringProperty descriptionProperty(){
	    	return description;
	    }
	    
	    public String getName() {
	        return name.get();
	    }
	    
	    public SimpleStringProperty nameProperty(){
	    	return name;
	    }
	    
	    public String getColor() {
	        return color.get();
	    }
	    
	    public SimpleStringProperty colorProperty(){
	    	return color;
	    }

	}
}



