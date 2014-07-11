package com.ezest.javafx.demogallery.tableviews;

import java.util.Set;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.ezest.javafx.components.EMRTableView;

public class TableAutoSizeDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configureTable();
	}

	private void configureTable() {
		
		StackPane sp1 = new StackPane();
		sp1.getChildren().add(new Label("Hel"));
		sp1.setStyle("-fx-border-width:1px;-fx-border-color:red;");
		StackPane sp2 = new StackPane();
		sp2.getChildren().add(new Label("Hel"));
		sp2.setStyle("-fx-border-width:1px;-fx-border-color:green;");
		
		GridPane gp = new GridPane();
		gp.setGridLinesVisible(true);
		ColumnConstraints c1 = new ColumnConstraints();
		c1.setPercentWidth(25);
		ColumnConstraints c2 = new ColumnConstraints();
		c2.setPercentWidth(75);
		gp.getColumnConstraints().addAll(c1, c2);
		gp.addRow(0, sp1 , sp2);
		root.getChildren().add(gp);
		
		
		final ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("Sai","This is for check. This is for check. This is for check. This is for check. This is for check. This is for check. This is for check. This is for check. This is for check. "),
				 new MyDomain("Pradeep","This is for check.")
			 );
		
	EMRTableView<MyDomain> table = new EMRTableView<MyDomain>(150);
	
	
	table.setTranslateY(30);
	table.getStyleClass().add("myTable");
	
	TableColumn<MyDomain,String> titleColumn = new TableColumn<MyDomain,String>("Titel");
	titleColumn.setPrefWidth(150);
	titleColumn.prefWidthProperty().bind(sp1.widthProperty());
	titleColumn.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("name"));
	
	Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>> cellFactory = new Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>>() {

		@Override
		public TableCell<MyDomain, String> call( TableColumn<MyDomain, String> param) {
			final TableCell<MyDomain, String> cell = new TableCell<MyDomain, String>() {
				private Text label;
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (!isEmpty()) {
						label = new Text(item.toString());
						label.setWrappingWidth(70);
						setGraphic(label);
					}
				}
			};
			return cell;
		}
		
	};
	titleColumn.setCellFactory(cellFactory);
	
	TableColumn<MyDomain,String> descCol = new TableColumn<MyDomain,String>("Description");
	descCol.prefWidthProperty().bind(sp2.widthProperty());
	descCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
	
	Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>> cellFactory2 = new Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>>() {

		@Override
		public TableCell<MyDomain, String> call( TableColumn<MyDomain, String> param) {
			final TableCell<MyDomain, String> cell = new TableCell<MyDomain, String>() {
				private Text label;
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (!isEmpty()) {
						label = new Text(item.toString());
						label.setWrappingWidth(70);
						setGraphic(label);
					}
				}
			};
			return cell;
		}
		
	};
	descCol.setCellFactory(cellFactory2);
	
	
	table.getColumns().addAll(titleColumn,descCol);
	table.setItems(data);
	
	root.getChildren().add(table);
		Set<Node> header = table.lookupAll("TableHeaderRow");
		//header.setVisible(false);
		//table.setLayoutY(-header.getHeight());
		//table.autosize();
			
		
	}

	private void configureStage(){
		stage.setTitle("TableAutoSizeDemo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(700);
	    stage.setHeight(400);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
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


