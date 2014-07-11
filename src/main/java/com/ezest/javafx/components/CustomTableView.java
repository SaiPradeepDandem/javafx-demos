package com.ezest.javafx.components;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class CustomTableView<S> extends StackPane{
	private TableView<S> table;
	private GridPane grid;
	
	@SuppressWarnings("rawtypes")
	public CustomTableView(){
		this.table = new TableView<S>();
		this.grid = new GridPane();
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
						NumberBinding diff = sp.widthProperty().subtract(3); // Quick fix for not showing the horizantal scroll bar.
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
	
	public static class CustomTableColumn<S,T> extends TableColumn<S, T>{
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
}
