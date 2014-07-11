package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StackPaneEffectIssueDemo extends Application {

	Stage stage;
	Scene scene;
	BorderPane root;
	TableView<Employee> tableView ;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureStageAndScene();
		
		Tab tab1 = new Tab("Tab 1");
		addTableInTab(tab1);
		
		Tab tab2 = new Tab("Tab 2");
		//addTableInTab(tab2);
		
		Tab tab3 = new Tab("Tab 3");
		//addTableInTab(tab3);
		
		TabPane tabPane = new TabPane();
		tabPane.getTabs().addAll(tab1, tab2, tab3);
		
		StackPane container = new StackPane();
		container.setPadding(new Insets(40));
		container.setStyle("-fx-effect: dropshadow(gaussian, #9CB6C3, 30, 0, 0, 0);");
		container.getChildren().add(tabPane);
		
		root.setCenter(container);
	}

	private void configureStageAndScene(){
		root = new BorderPane();
		this.scene = new Scene(root, Color.LINEN);
		
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void addTableInTab(Tab tb){
		
		tableView =new TableView<Employee>();
		
		TableColumn<Employee, String> col1 = new TableColumn<Employee, String>("First Name");
		col1.setPrefWidth(150);
		col1.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		
		TableColumn<Employee, String> col2 = new TableColumn<Employee, String>("Last Name");
		col2.setPrefWidth(150);
		col2.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		
		TableColumn<Employee, String> col3 = new TableColumn<Employee, String>("Country");
		col3.setPrefWidth(150);
		col3.setCellValueFactory(new PropertyValueFactory<Employee, String>("country"));
		
		tableView.getColumns().addAll(col1,col2,col3);
		setEmployees();
		tb.setContent(tableView);
	}
	
	private void setEmployees(){
		ObservableList<Employee> list = FXCollections.observableArrayList();
		for (int i = 0; i < 10; i++) {
			list.add(new Employee("first name "+i, "last name "+i, "country "+i));
		}
		tableView.setItems(list);
	}
	
	
}
class Employee{
	private String firstName;
	private String lastName;
	private String country;
	public Employee(String firstName, String lastName, String country) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}

