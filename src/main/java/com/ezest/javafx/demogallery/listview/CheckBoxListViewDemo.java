package com.ezest.javafx.demogallery.listview;

import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckBoxListViewDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;

	public static void main(String[] args) {
		Application.launch(args);
	}

	private List<Employee> getData() {
		return Arrays.<Employee> asList(new Employee(1, "Employee1"), new Employee(2, "Employee2"), new Employee(3, "Employee3"),
				new Employee(4, "Employee4"), new Employee(5, "Employee5"), new Employee(6, "Employee6"), new Employee(7, "Employee7"),
				new Employee(8, "Employee8"), new Employee(9, "Employee9"), new Employee(10, "Employee10"), new Employee(11, "Employee11"),
				new Employee(12, "Employee12"), new Employee(13, "Employee13"), new Employee(14, "Employee14"), new Employee(15,
						"Employee15"), new Employee(16, "Employee16"));
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		// Logic starts
		HBox hb = new HBox();
		hb.setSpacing(20);
		final CheckBoxListView<Employee> listView = new CheckBoxListView<>();

		final VBox vb = new VBox();
		listView.selectedItemsProperty().addListener(new ListChangeListener<Employee>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Employee> arg0) {
				vb.getChildren().clear();
				for (Employee emp : listView.getSelectedItems()) {
					vb.getChildren().add(new Text(emp.getName()));
				}
			}
		});
		listView.setItems(FXCollections.observableArrayList(getData()));
		hb.getChildren().addAll(listView, vb);
		root.getChildren().add(hb);
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(600);
		stage.setHeight(600);
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
		scene.getStylesheets().add("com/ezest/javafx/demogallery/listview/CheckBoxListView.css");
	}

	class Employee {
		private final SimpleLongProperty id;
		private final SimpleStringProperty name;

		public Employee(long id, String name) {
			this.id = new SimpleLongProperty(id);
			this.name = new SimpleStringProperty(name);
		}

		public long getId() {
			return id.get();
		}

		public void setId(long id) {
			this.id.set(id);
		}

		public String getName() {
			return name.get();
		}

		public void setName(String fName) {
			name.set(fName);
		}

		@Override
		public String toString() {
			return getName();
		}
	}
}
