package com.ezest.javafx.demogallery.listview;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CheckBoxListCellDemo extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	CheckBox cb = CheckBoxBuilder.create().build();
	final ObservableList<Employee> data = FXCollections.observableArrayList();
	
	ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
		@Override
		public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1, Boolean selected) {
			if(selected){
				boolean flag = true;
				for (Employee emp : data) {
					if(!emp.getSelected()){
						flag = false;
						break;
					}
				}
				if(flag){
					cb.setSelected(true);
				}
			}else{
				cb.setSelected(false);
			}
		}
	};
	@Override
	public void start(Stage primaryStage) {
		cb.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1, Boolean paramT2) {
				if(paramT2){
					for (Employee emp : data) {
						emp.setSelected(true);
					}
				}
			}
		});
		primaryStage.setTitle("CheckBoxListCell Sample");

		for (int i = 0; i < 5; i++) {
			data.add(new Employee(i % 2 == 0 ? true : false, "Empl " + i));
		}

		
		final ListView<Employee> listView = new ListView<Employee>();
		listView.setPrefSize(200, 250);
		listView.setEditable(true);
		
		for (Employee employee : data) {
			employee.selectedProperty().addListener(listener);
		}
		listView.setItems(data);

		Callback<Employee, ObservableValue<Boolean>> getProperty = new Callback<Employee, ObservableValue<Boolean>>() {
			@Override
			public BooleanProperty call(Employee layer) {

				return layer.selectedProperty();

			}
		};
		Callback<ListView<Employee>, ListCell<Employee>> forListView = CheckBoxListCell.forListView(getProperty);
		listView.setCellFactory(forListView);

		StackPane root = new StackPane();
		Button btn = new Button("Show");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				for (Employee employee : data) {
					System.out.println(employee.getSelected());
				}
			}
		});
		VBox vb = new VBox();
		vb.getChildren().addAll(listView, btn, cb);
		root.getChildren().add(vb);
		primaryStage.setScene(new Scene(root, 200, 250));
		primaryStage.show();
	}

	class Employee {
		private final SimpleBooleanProperty selected;
		private final SimpleStringProperty name;

		public Employee(boolean id, String name) {
			this.selected = new SimpleBooleanProperty(id);
			this.name = new SimpleStringProperty(name);
		}

		public boolean getSelected() {
			return selected.get();
		}

		public void setSelected(boolean selected) {
			this.selected.set(selected);
		}

		public String getName() {
			return name.get();
		}

		public void setName(String fName) {
			name.set(fName);
		}

		public SimpleBooleanProperty selectedProperty() {
			return selected;
		}

		@Override
		public String toString() {
			return getName();
		}
	}
}
