package com.ezest.javafx.demogallery.tableviews;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ExtraColumnStying extends Application{
	
	StackPane root;
	private Timeline clickTimer = new Timeline();
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new StackPane();
		root.autosize();
		Scene scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
		
		stage.setTitle("Extra Column Styling");
		stage.setWidth(700);
	    stage.setHeight(400);
	    stage.setScene(scene);
	    stage.show();
	    
	    configureTable();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@SuppressWarnings("unchecked")
	private void configureTable() {
		final ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("First Row","This is for check."),
				 new MyDomain("Second Row","This is for check.")
			 );
		
		TableView<MyDomain> tableView = new TableView<MyDomain>();
		tableView.getStyleClass().add("myTable");
		tableView.setItems(data);
		
		TableColumn<MyDomain, String> column1 = new TableColumn<ExtraColumnStying.MyDomain, String>("Title");
		column1.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("name"));
		
		TableColumn<MyDomain, String> column2 = new TableColumn<ExtraColumnStying.MyDomain, String>("Description");
		column2.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("description"));
		
		tableView.getColumns().addAll(column1, column2);
		this.root.getChildren().add(tableView);
		
		
		this.clickTimer.getKeyFrames().add(new KeyFrame(Duration.millis(2000), new KeyValue[0]));
		this.clickTimer.setOnFinished(new EventHandler()
		{
			@Override
			public void handle(Event paramT) {
				System.out.println("Row is selected");
			}
		});
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MyDomain>() {
			@Override
			public void changed(ObservableValue<? extends MyDomain> arg0,MyDomain arg1, MyDomain arg2) {
				System.out.println("Selection changed");
				if (clickTimer.getStatus() == Animation.Status.RUNNING){
					clickTimer.stop();
				}
				clickTimer.play();
			}
		});
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
