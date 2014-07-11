package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TableViewSorting extends Application{

	StackPane root;
	private ObservableList<MyDomain> data = FXCollections.observableArrayList(
			new MyDomain("John","Albert"),
			new MyDomain("Rama","Lord"),
			new MyDomain("James","Watson")
	);

	// Variables used to track the last sorting, and used when new data is changed.
	private SortType sortType = null;
	private TableColumn<MyDomain, String> sortColumn = null;

	@Override
	public void start(Stage stage) throws Exception {
		root = new StackPane();
		root.autosize();
		
		stage.setTitle("Table View Sorting");
		stage.setWidth(700);
		stage.setHeight(400);
		stage.setScene(new Scene(root, Color.LINEN));
		stage.show();

		
		final TextField firstNameTxt = new TextField();
		firstNameTxt.setMinHeight(26);
		final TextField lastNameTxt = new TextField();
		lastNameTxt.setMinHeight(26);
		Button btn = new Button("Add Row");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				data.add(new MyDomain(firstNameTxt.getText(), lastNameTxt.getText()));
				firstNameTxt.setText(null);
				lastNameTxt.setText(null);
			}
		});
		GridPane gp = new GridPane();
		gp.setHgap(10);
		gp.setVgap(10);
		gp.add(new Label("Enter First Name : "), 0, 0);
		gp.add(firstNameTxt, 1, 0);
		gp.add(new Label("Enter Last Name : "), 0, 1);
		gp.add(lastNameTxt, 1, 1);
		gp.add(btn, 0, 2);
		
		VBox vb = new VBox();
		vb.getChildren().add(gp);
		
		configureTable(vb);
		
		this.root.getChildren().add(vb);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@SuppressWarnings("unchecked")
	private void configureTable(VBox vb) {
		
		final TableView<MyDomain> tableView = new TableView<MyDomain>();
		tableView.getStyleClass().add("myTable");
		tableView.setItems(data);
		data.addListener(new ListChangeListener<MyDomain>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends MyDomain> paramChange) {
				if (sortColumn != null && paramChange.getAddedSize()>0) {
					if (sortType == null) {
						sortType = SortType.ASCENDING;
					}

					sortColumn.setSortType(sortType);
					tableView.getSortOrder().clear();
					tableView.getSortOrder().add(sortColumn);
				}
			}
		});
		final TableColumn<MyDomain, String> column1 = new TableColumn<MyDomain, String>("FirstName");
		column1.setPrefWidth(150);
		column1.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("firstName"));
		column1.sortTypeProperty().addListener(new ChangeListener<SortType>() {
			@Override
			public void changed(ObservableValue<? extends SortType> paramObservableValue, SortType paramT1, SortType paramT2) {
				sortType = paramT2;
				sortColumn = column1;
			}
		});
		
		final TableColumn<MyDomain, String> column2 = new TableColumn<MyDomain, String>("LastName");
		column2.setPrefWidth(150);
		column2.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("lastName"));
		column2.sortTypeProperty().addListener(new ChangeListener<SortType>() {
			@Override
			public void changed(ObservableValue<? extends SortType> paramObservableValue, SortType paramT1, SortType paramT2) {
				sortType = paramT2;
				sortColumn = column2;
			}
		});
		
		tableView.getColumns().addAll(column1, column2);
		vb.getChildren().add(tableView);
	}

	public class MyDomain{
		private SimpleStringProperty firstName = new SimpleStringProperty();
		private SimpleStringProperty lastName = new SimpleStringProperty();
		public MyDomain(String firstName, String lastName){
			this.firstName.set(firstName);
			this.lastName.set(lastName);

		}
		public MyDomain(){}

		public String getLastName() {
			return lastName.get();
		}

		public SimpleStringProperty lastNameProperty(){
			return lastName;
		}

		public String getFirstName() {
			return firstName.get();
		}

		public SimpleStringProperty firstNameProperty(){
			return firstName;
		}

	}
}

