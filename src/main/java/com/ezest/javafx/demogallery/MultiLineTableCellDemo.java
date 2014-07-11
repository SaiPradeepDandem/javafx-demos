package com.ezest.javafx.demogallery;

import com.ezest.javafx.components.EMRTableView;
import com.ezest.javafx.domain.MyDomain;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MultiLineTableCellDemo extends Application {

	private FlowPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new FlowPane();
		root.setVgap(10);
		root.setHgap(10);
		Scene scene = new Scene(root, Color.LINEN);
		stage.setTitle("Multi Line Table Cell Demo");
		stage.setWidth(800);
	    stage.setHeight(500);
	    stage.setScene(scene);
	    scene.getStylesheets().add("styles/sample.css");
	    
	    showMultiLineTable();
	    showEmptyRowTable();
	    stage.show();
	}

	
	private void showMultiLineTable() {
		 
		final ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("Ram","This is Ram. This is for the check of multi line table cell."),
				 new MyDomain("Robert","This is Robert. This is for the check of multi line table cell.")
			);
		
		TableView tableView = new TableView();
		tableView.setPrefHeight(250);
		tableView.setPrefWidth(270);
		
		TableColumn nameCol = new TableColumn("Name");
		nameCol.setPrefWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("name"));
		Callback<TableColumn, TableCell> cellFactory1 = new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call( TableColumn param) {
				final TableCell cell = new TableCell() {
					@Override
					public void updateItem(Object item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							setGraphic(new Text(item.toString()));
						}
					}
				};
				cell.setAlignment(Pos.TOP_LEFT);
				return cell;
			}
		};
		nameCol.setCellFactory(cellFactory1);
		
		TableColumn descCol = new TableColumn("Description");
		descCol.setPrefWidth(150);
		descCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
		Callback<TableColumn, TableCell> cellFactory2 = new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call( TableColumn param) {
				final TableCell cell = new TableCell() {
					private Text label;
					@Override
					public void updateItem(Object item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							label = new Text(item.toString());
							label.setWrappingWidth(140);
							setGraphic(label);
						}
					}
				};
				return cell;
			}
		};
		descCol.setCellFactory(cellFactory2);
		
		tableView.getColumns().addAll(nameCol,descCol);
		tableView.setItems(data);
		
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.getChildren().addAll(new Label("Multi line table cell : "),tableView);
		
		root.getChildren().add(vb);
	}

	private void showEmptyRowTable() {
		final ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("Ram","This is Ram. "),
				 new MyDomain("Robert","This is Robert. ")
			);
		
		TableView tableView = new TableView();
		tableView.getStyleClass().add("emptyRowTableView");
		tableView.setPrefHeight(250);
		tableView.setPrefWidth(270);
		
		TableColumn nameCol = new TableColumn("Name");
		nameCol.setPrefWidth(100);
		nameCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("name"));
		
		TableColumn descCol = new TableColumn("Description");
		descCol.setPrefWidth(150);
		descCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
		
		tableView.getColumns().addAll(nameCol,descCol);
		tableView.setItems(data);
		
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.getChildren().addAll(new Label("Empty cell background : "),tableView);
		
		root.getChildren().add(vb);
		
	}

}
