package com.ezest.javafx.demogallery.tableviews;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.ezest.javafx.domain.MyDomain;

public class CustomTableHeaderDemo extends Application {

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

	@SuppressWarnings("unchecked")
	private void configureTable() {
		TableView<MyDomain> table = new TableView<MyDomain>();
		
		TableColumn<MyDomain,String> titleColumn = new TableColumn<MyDomain,String>("Title");
		titleColumn.setPrefWidth(150);
		titleColumn.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("name"));
		
		TableColumn<MyDomain,String> descColumn = new TableColumn<MyDomain,String>("Description");
		descColumn.setPrefWidth(150);
		descColumn.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
		descColumn.setCellFactory(new Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>>() {
			
			@Override
			public TableCell<MyDomain, String> call(TableColumn<MyDomain, String> arg0) {
			
				return new TableCell<MyDomain, String>(){
					@Override
					protected void updateItem(String arg0, boolean arg1) {
						super.updateItem(arg0, arg1);
						if(!arg1){
							setGraphic(new Label(arg0));
						}else{
							setGraphic(null);
						}
					}
				};
			}
		});
		table.getColumns().addAll(titleColumn,descColumn);
		table.setItems(TableData.getData());
		
		root.getChildren().add(table);
	}
	
	private void configureStage(){
		stage.setTitle("Custom Table Header Demo");
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
		scene.getStylesheets().add("styles/tableviewdemos.css");
	}
}

class MyChoiceBox<T> extends ChoiceBox<T>{
	private ObservableList<T> myItems = FXCollections.observableArrayList();
	
	public MyChoiceBox(){
		super();
	}
}
