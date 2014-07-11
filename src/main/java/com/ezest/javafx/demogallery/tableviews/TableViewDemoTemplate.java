package com.ezest.javafx.demogallery.tableviews;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TableViewDemoTemplate extends Application {
	TableView<MyDomain> table;
	TableColumn<MyDomain, String> col1;
	TableColumn<MyDomain, String> col2;
	TableColumn<MyDomain, String> col3;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		root.setSpacing(5);
		root.autosize();
		Scene scene = new Scene(root);
		stage.setTitle("TableView Demo");
		stage.setWidth(700);
		stage.setHeight(400);
		stage.setScene(scene);
		stage.show();
		configureTable(root);
	}

	@SuppressWarnings("unchecked")
	private void configureTable(VBox root) {
		final ObservableList<MyDomain> data = FXCollections.observableArrayList();
		for (int i = 0; i < 2; i++) {
			data.add(new MyDomain("Apple", "This is a fruit.", "Red"));
			data.add(new MyDomain("Orange", "This is also a fruit.", "Orange"));
			data.add(new MyDomain("Apple", "This is a fruit.", "Red"));
		}
		table = new TableView<MyDomain>();

		col1 = new TableColumn<MyDomain, String>("Name");
		col1.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("name"));

		col2 = new TableColumn<MyDomain, String>("Description");
		col2.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("description"));

		col3 = new TableColumn<MyDomain, String>("Color");
		col3.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("color"));

		table.getColumns().addAll(col1, col2, col3);
		table.setItems(data);

		Button btn = new Button("Add");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				table.getItems().add(new MyDomain("Apple", "This is a fruit.", "Red"));
			}
		});
		Button del = new Button("Del");
		del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				data.remove(0);
			}
		});

		root.getChildren().addAll(btn, del, table);
		VBox.setVgrow(table, Priority.ALWAYS);

	}

	/**
	 * Domain Object.
	 */
	public class MyDomain {
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();
		private SimpleStringProperty color = new SimpleStringProperty();

		public MyDomain(String name, String desc, String color) {
			this.name.set(name);
			this.description.set(desc);
			this.color.set(color);
		}

		public String getDescription() {
			return description.get();
		}

		public SimpleStringProperty descriptionProperty() {
			return description;
		}

		public String getName() {
			return name.get();
		}

		public SimpleStringProperty nameProperty() {
			return name;
		}

		public String getColor() {
			return color.get();
		}

		public SimpleStringProperty colorProperty() {
			return color;
		}
	}
}
