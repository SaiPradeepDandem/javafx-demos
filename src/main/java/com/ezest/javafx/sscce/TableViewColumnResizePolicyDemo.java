package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableViewColumnResizePolicyDemo extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		root.autosize();
		Scene scene = new Scene(root);
		stage.setTitle("TableView Column Resize Policy Demo");
		stage.setWidth(700);
	    stage.setHeight(400);
	    stage.setScene(scene);
	    stage.show();
	    
		configureTable(root);
	}

	private void configureTable(VBox root) {
		
		final ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("Apple","This is a fruit.","Red"),
				 new MyDomain("Orange","This is also a fruit.","Orange"),
				 new MyDomain("Potato","This is a vegetable.","Brown")
				 );
		
		TableView<MyDomain> table1 = getTableView(data);
		table1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableView<MyDomain> table2 = getTableView(data);
		table2.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		root.setSpacing(15);
		root.getChildren().addAll(LabelBuilder.create().text("CONSTRAINED_RESIZE_POLICY").style("-fx-font-weight:bold;").build(),table1,
								  LabelBuilder.create().text("UNCONSTRAINED_RESIZE_POLICY").style("-fx-font-weight:bold;").build(),table2);
	}
	
	private TableView<MyDomain> getTableView(ObservableList<MyDomain> data){
		TableView<MyDomain> table = new TableView<MyDomain>();
		
		TableColumn<MyDomain,String> titleColumn = new TableColumn<MyDomain,String>("Title");
		titleColumn.setPrefWidth(100);
		titleColumn.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("name"));
		
		TableColumn<MyDomain,String> descCol = new TableColumn<MyDomain,String>("Description");
		descCol.setPrefWidth(250);
		descCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
		
		TableColumn<MyDomain,String> colorCol = new TableColumn<MyDomain,String>("Color");
		colorCol.setPrefWidth(100);
		colorCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("color"));
		
		table.getColumns().addAll(titleColumn,descCol,colorCol);
		table.setItems(data);
		return table;
	}
	

	
	/**
	 * Domain Object.
	 */
	public class MyDomain{
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();
		private SimpleStringProperty color = new SimpleStringProperty();
		public MyDomain(String name, String desc,String color){
			this.name.set(name);
			this.description.set(desc);
			this.color.set(color);
		}
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





