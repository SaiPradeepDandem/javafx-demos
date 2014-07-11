package com.ezest.javafx.demogallery.tableviews;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class SimpleTableViewDemo extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		StackPane root = new StackPane();
		root.autosize();
		Scene scene = new Scene(root);
		stage.setTitle("Simple TableView Demo");
		stage.setWidth(700);
		stage.setHeight(400);
		stage.setScene(scene);
		stage.show();
		ScenicView.show(scene);

		configureTable(root);
	}

	@SuppressWarnings("unchecked")
	private void configureTable(StackPane root) {

		final ObservableList<MyDomain> data = FXCollections.observableArrayList(new MyDomain("Apple", "This is a fruit.", "Red"),
				new MyDomain("Orange", "This is also a fruit.", "Orange"), new MyDomain("Potato", "This is a vegetable.", "Brown"));

		final TableView<MyDomain> table = new TableView<MyDomain>();
		table.setManaged(true);
		TableColumn<MyDomain, String> titleColumn = new TableColumn<MyDomain, String>("Title");
		titleColumn.setPrefWidth(125);
		titleColumn.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("name"));
		titleColumn.sortTypeProperty().addListener(new ChangeListener<SortType>() {
			@Override
			public void changed(ObservableValue<? extends SortType> arg0, SortType arg1, SortType arg2) {
				System.out.println(arg2);
			}
		});
		TableColumn<MyDomain, String> descCol = new TableColumn<MyDomain, String>("Description");
		descCol.setPrefWidth(200);
		descCol.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("description"));

		TableColumn<MyDomain, String> colorCol = new TableColumn<MyDomain, String>("Color");
		colorCol.setPrefWidth(200);
		colorCol.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("color"));

		table.getColumns().addAll(titleColumn, descCol, colorCol);
		table.itemsProperty().addListener(new ChangeListener<ObservableList<MyDomain>>() {
			@Override
			public void changed(ObservableValue<? extends ObservableList<MyDomain>> paramObservableValue, ObservableList<MyDomain> paramT1,
					ObservableList<MyDomain> paramT2) {
				table.setPrefHeight((table.getItems().size()*25) + 25);
			}});
		table.setItems(data);
		VBox vb = new VBox();
		vb.getChildren().addAll(StackPaneBuilder.create().prefHeight(100).style("-fx-background-color:red;").build(), table,
				StackPaneBuilder.create().prefHeight(100).style("-fx-background-color:blue;").build());
		root.getChildren().add(vb);

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
