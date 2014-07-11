package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class WrappingTextDemo extends Application
{

	public void start(Stage stage) throws Exception
	{
		StackPane root = new StackPane();
		root.autosize();
		Scene scene = new Scene(root);
		stage.setTitle("TableView Wrapping Text Demo");
		stage.setWidth(700);
		stage.setHeight(400);
		stage.setScene(scene);
		stage.show();

		configureTable(root);
	}

	@SuppressWarnings("unchecked")
	private void configureTable(StackPane root) {

		ObservableList<MyDomain> data = FXCollections.observableArrayList(
				new MyDomain("Apple","This is a long description. This is a long description. This is a long description. This is a long description. This is a long description. This is a long description. "),
				new MyDomain("Orange","This is a long description. This is a long description. This is a long description. This is a long description. This is a long description. This is a long description. ")
		);

		TableView<MyDomain> tableView = new TableView<MyDomain>();
		tableView.getStyleClass().add("myTable");
		tableView.setItems(data);

		TableColumn<MyDomain, String> column1 = new TableColumn<MyDomain, String>("Title");
		column1.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("name"));

		TableColumn<MyDomain, String> column2 = new TableColumn<MyDomain, String>("Description");
		column2.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("description"));
		column2.setCellFactory(new Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>>() {
			@Override
			public TableCell<MyDomain, String> call( TableColumn<MyDomain, String> param) {
				final TableCell<MyDomain, String> cell = new TableCell<MyDomain, String>() {
					private Text text;
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							text = new Text(item.toString());
							text.setWrappingWidth(200); // Setting the wrapping width to the Text
							setGraphic(text);
						}
					}
				};
				return cell;
			}
		});

		tableView.getColumns().addAll(column1, column2);
		root.getChildren().add(tableView);
	}

	public static void main(String[] args) throws Exception { launch(args); }

	public class MyDomain{
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();

		public MyDomain(String name, String desc){
			this.name.set(name);
			this.description.set(desc);
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
	}
}
