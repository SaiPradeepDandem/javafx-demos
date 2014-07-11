/**
 * 
 */
package com.ezest.javafx.demogallery.tableviews;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

/**
 * @author Sai.Dandem
 * 
 */
public class AutoAdjustableColumnsDemo extends Application {
	TableView<MyDomain> tableView;
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
		tableView = new TableView<MyDomain>();

		col1 = new TableColumn<MyDomain, String>("Name");
		col1.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("name"));

		col2 = new TableColumn<MyDomain, String>("Description");
		col2.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("description"));

		col3 = new TableColumn<MyDomain, String>("Color");
		col3.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("color"));

		tableView.getColumns().addAll(col1, col2, col3);
		tableView.setItems(data);

		//set optimal size
		final ObservableList<TableColumn<MyDomain, ?>> columns = tableView.getColumns();
		final ObservableList<TableColumn<MyDomain, ?>> visibleLeafColumns = tableView.getVisibleLeafColumns();
		final int size = visibleLeafColumns.size();
		final SimpleDoubleProperty newSizeProperty = new SimpleDoubleProperty(size);
		visibleLeafColumns.addListener(new ListChangeListener<TableColumn<MyDomain, ?>>() {
		    @Override public void onChanged(Change<? extends TableColumn<MyDomain, ?>> change) {
		        final int newSize = change.getList().size();
		        newSizeProperty.set(newSize);
		    }
		});
		for (final TableColumn<MyDomain, ?> column : columns) {
		    column.prefWidthProperty().bind(tableView.widthProperty().divide(newSizeProperty));
		}
		
		Button btn = new Button("Add");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				tableView.getItems().add(new MyDomain("Apple", "This is a fruit.", "Red"));
			}
		});
		Button del = new Button("Del");
		del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				data.remove(0);
			}
		});

		root.getChildren().addAll(btn, del, tableView);
		VBox.setVgrow(tableView, Priority.ALWAYS);

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
