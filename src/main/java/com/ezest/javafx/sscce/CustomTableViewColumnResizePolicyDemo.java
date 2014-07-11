package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomTableViewColumnResizePolicyDemo extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		root.autosize();
		Scene scene = new Scene(root);
		stage.setTitle("Custom TableView Column Resize Policy Demo");
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
		
		CustomTableView<MyDomain> table1 = getTableView(data);
		table1.getTableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		CustomTableView<MyDomain> table2 = getTableView(data);
		table2.getTableView().setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		
		root.setSpacing(15);
		root.getChildren().addAll(LabelBuilder.create().text("CONSTRAINED_RESIZE_POLICY").style("-fx-font-weight:bold;").build(),table1,
								  LabelBuilder.create().text("UNCONSTRAINED_RESIZE_POLICY").style("-fx-font-weight:bold;").build(),table2);
	}
	
	private CustomTableView<MyDomain> getTableView(ObservableList<MyDomain> data){
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
		return table;
	}
	
	/**
	 * CustomTableView to hold the table and grid.
	 */
	public class CustomTableView<S> extends StackPane{
		private TableView<S> table;
		
		@SuppressWarnings("rawtypes")
		public CustomTableView(){
			this.table = new TableView<S>();
			//this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			this.table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
			final GridPane grid = new GridPane();
			this.table.getColumns().addListener(new ListChangeListener<TableColumn>(){
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends TableColumn> arg0) {
					grid.getColumnConstraints().clear();
					ColumnConstraints[] arr1 = new ColumnConstraints[CustomTableView.this.table.getColumns().size()];
					StackPane[] arr2 = new StackPane[CustomTableView.this.table.getColumns().size()];
					int i=0;
					for(TableColumn column : CustomTableView.this.table.getColumns()){
						CustomTableColumn col = (CustomTableColumn)column;
						ColumnConstraints consta = new ColumnConstraints();
						consta.setPercentWidth(col.getPercentWidth());
						
						StackPane sp = new StackPane();
						if(i==0){
							// Quick fix for not showing the horizantal scroll bar.
							NumberBinding diff = sp.widthProperty().subtract(3.75); 
							column.prefWidthProperty().bind(diff);
						}else{
							column.prefWidthProperty().bind(sp.widthProperty());
						}
						arr1[i] = consta;
						arr2[i] = sp;
						i++;
					}
					grid.getColumnConstraints().addAll(arr1);
					grid.addRow(0, arr2);
				}
			});
			getChildren().addAll(grid,table);
		}
		
		public TableView<S> getTableView(){
			return this.table;
		}
	}
	
	/**
	 * CustomTableColumn to hold the custom percentWidth property.
	 */
	public class CustomTableColumn<S,T> extends TableColumn<S, T>{
		private SimpleDoubleProperty percentWidth = new SimpleDoubleProperty();
		public CustomTableColumn(String columnName){
			super(columnName);
		}
		public SimpleDoubleProperty percentWidth() {
			return percentWidth;
		}
		public double getPercentWidth() {
			return percentWidth.get();
		}
		public void setPercentWidth(double percentWidth) {
			this.percentWidth.set(percentWidth);
		}
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




