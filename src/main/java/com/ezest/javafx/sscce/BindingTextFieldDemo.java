package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BindingTextFieldDemo extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		StackPane root = new StackPane();
		Scene scene = new Scene(root, Color.LINEN);
		stage.setWidth(300);
	    stage.setHeight(300);
	    stage.setScene(scene);
	    stage.show();
	    configureForm(root);
	}

	private void configureForm(StackPane root) {
		final Person person = new Person();
		person.setName("Sai PradeeP");
		VBox layout = new VBox();
		layout.setSpacing(15);
		TextField nameField = new TextField();
		nameField.textProperty().bindBidirectional(person.nameProperty());
		
		HBox nameLayout = new HBox();
		nameLayout.getChildren().addAll(new Label("Enter the name : "),nameField);
		
		HBox displayLayout = new HBox();
		final Label lbl = new Label();
		displayLayout.getChildren().addAll(new Label("You have entered : "),lbl);
		
		Button btn = new Button("Submit");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				lbl.setText(person.getName());
			}
		});
		
		layout.getChildren().addAll(nameLayout,btn,new Separator(),displayLayout);
		root.getChildren().addAll(layout);
		
	}
	
	//--------------Person Class----------------------------------------------------
	class Person{
		private SimpleStringProperty name = new SimpleStringProperty("");

		public String getName() {
			return name.get();
		}

		public void setName(String name) {
			this.name.set(name);
		}
		
		public SimpleStringProperty nameProperty(){
			return this.name;
		}
	}

}
