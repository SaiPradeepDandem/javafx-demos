package com.ezest.javafx.demogallery.tableviews;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.sun.javafx.scene.control.skin.TableColumnHeader;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;

public class GhostColumnRemover extends Application{
	
	StackPane root;
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new StackPane();
		root.autosize();
		Scene scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("com/ezest/javafx/demogallery/tableviews/GhostColumnRemover.css");
		
		stage.setTitle("Extra Column Styling");
		stage.setWidth(700);
	    stage.setHeight(400);
	    stage.setScene(scene);
	    stage.show();
	    
	    configureTable();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void configureTable() {
		final ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("First Row","This is for check."),
				 new MyDomain("Second Row","This is for check.")
			 );
		
		TableView<MyDomain> tableView = new TableView<MyDomain>();
		tableView.getStyleClass().add("myTable");
		tableView.setItems(data);
		
		TableColumn<MyDomain, String> column1 = new TableColumn<GhostColumnRemover.MyDomain, String>("Title");
		column1.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("name"));
		
		TableColumn<MyDomain, String> column2 = new TableColumn<GhostColumnRemover.MyDomain, String>("Description");
		column2.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("description"));
		column2.setCellFactory(new Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>>() {
			@Override
			public TableCell<MyDomain, String> call(TableColumn<MyDomain, String> paramP) {
				return new TableCell<GhostColumnRemover.MyDomain, String>(){
					@Override
					protected void updateItem(String paramT, boolean paramBoolean) {
						super.updateItem(paramT, paramBoolean);
						if(!isEmpty()){
							setGraphic(new Label(paramT));
						}
						this.getStyleClass().add("last-table-cell");
						TableViewSkin<MyDomain> skin = (TableViewSkin<MyDomain>)this.getTableView().getSkin();
						// System.out.println(skin.getChildren());
						// [TableHeaderRow@2b24e6[styleClass=column-header-background], 
						// VirtualFlow@83a95d, Region@1e4318d[styleClass=column-overlay], Region@712a2e[styleClass=column-resize-line]]
						
						
						StackPane headerrow = (StackPane)skin.getChildren().get(0);
						/*  System.out.println(headerrow.getChildren());
						 
							[NestedTableColumnHeader@1562f00[styleClass=nested-column-header], 
							Region@f5f6dc[styleClass=filler], 
							TableHeaderRow$4@2203c1[styleClass=show-hide-columns-button], 
							StackPane@124aa56[styleClass=column-drag-header]]
						*/
						
						TableColumnHeader head = (TableColumnHeader)headerrow.getChildren().get(0);
						System.out.println(headerrow.getChildren().get(0));
						head.getStyleClass().add("my-column1");
						TableColumnHeader firstno = (TableColumnHeader)head.getChildren().get(0);
						firstno.getStyleClass().add("my-column");
						
						VirtualFlow flow = (VirtualFlow)skin.getChildren().get(1);
						System.out.println("** "+flow.getChildrenUnmodifiable());
								
					}
				};
			}
		});
		
		tableView.getColumns().addAll(column1, column2);
		this.root.getChildren().add(tableView);
	}

	public class MyDomain{
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();
		MyDomain(String name, String desc){
			this.name.set(name);
			this.description.set(desc);
			
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

	}
}

