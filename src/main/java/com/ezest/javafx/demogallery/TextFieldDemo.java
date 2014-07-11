package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TooltipBuilder;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TextFieldDemo extends Application {

	Stage stage;
	Scene scene;
	VBox root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		setTextLimitField();
		setIntegerTextField();
		setSnipperTextField();
		setBindingTextField();
	}

	

	private void configureStage(){
		stage.setTitle("Text Field Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new VBox();
		root.autosize();
	    this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

	private void setTextLimitField() {
		final CustomTextField tbox = new CustomTextField();
		
			
		final int max_length = 6;
		tbox.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				String value = ((StringProperty)observable).get();
				if(value.length()>max_length){
                    tbox.textProperty().setValue(value.substring(0,max_length));
				}
			}
		});
		
		Text lbl = new Text("This field limits text to "+max_length+" characters : ");
		//lbl.setLabelFor(tbox);
		//lbl.setFontSmoothingType(FontSmoothingType.LCD);
		lbl.setFont(Font.font("Tahoma", 25));
		
		tbox.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean paramT2) {
				if(paramT2){
					if(tbox.getText()!=null && tbox.getText().length()>0){
						tbox.selectAll();
						//tbox.selectRange(0, tbox.getText().length()-1);
					}
				}
			}
		});
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setPadding(new Insets(10));
		hb.getChildren().addAll(lbl,tbox);
		
		Group gp = new Group();
		gp.getChildren().add(hb);
		
		root.getChildren().add(gp);
		
	}
	
	private void setIntegerTextField() {
		final TextField tbox = new TextField();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				tbox.requestFocus();
			}
		});
		
		tbox.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			 public void handle(KeyEvent t) {
		          if (!"0123456789".contains(t.getCharacter())) {
		             t.consume();
		          }
		     }
		});

		Label lbl = new Label("This field will enter only Integer values : ");
		lbl.setLabelFor(tbox);
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setPadding(new Insets(10));
		hb.getChildren().addAll(lbl,tbox);
		
		Group gp = new Group();
		gp.getChildren().add(hb);
		
		root.getChildren().add(gp);
	}

	private void setSnipperTextField() {
		HBox spinner = new HBox();
		
		Label lbl = new Label("This field will increment and decrement the values in field : ");
		spinner.getChildren().add(lbl);
		
		TextField field = new TextField("Test");
		spinner.getChildren().add(field);
		 
		VBox buttonPane = new VBox();
		 
		String buttonStyle = new StringBuilder()
				.append("-fx-padding: 0 6 0 6;")
				.append("-fx-border-radius: 2;")
		        .append("-fx-background-radius: 2;")
		        .append("-fx-font-size: 8;")
		        .toString();
		 
		Button upButton = new Button("u");
		upButton.setStyle(buttonStyle);
		VBox.setVgrow(upButton, Priority.ALWAYS);
		buttonPane.getChildren().add(upButton);
		 
		Button downButton = new Button("d");
		downButton.setStyle(buttonStyle);
		VBox.setVgrow(downButton, Priority.ALWAYS);
		buttonPane.getChildren().add(downButton);
		 
		spinner.getChildren().add(buttonPane);
		
		root.getChildren().add(spinner);
	}
	private void setBindingTextField() {
		HBox binder = new HBox();
		
		Label lbl = new Label("This field is binded with the variable of an object : ");
		binder.getChildren().add(lbl);
		final MyObject obj = new MyObject();
		
		TextField field = new TextField("S");
		obj.nameProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(
					ObservableValue<? extends String> paramObservableValue,	String paramT1, String paramT2) {
				// TODO Auto-generated method stub
				
			}
		});
		field.textProperty().bind(obj.nameProperty()); //Shwoing null pointer exception
		binder.getChildren().add(field);
		 
		Button upButton = new Button("Show");
		upButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent paramT) {
				System.out.println(obj.getName());
			}
		});
		binder.getChildren().add(upButton);
		
		root.getChildren().add(binder);
	}
	
	class CustomTextField extends TextField{
		public CustomTextField(){
			super();
			addListener();
			
			VBox vb = new VBox();
			vb.getChildren().add(new Label("Hello dude"));
			vb.getChildren().add(LabelBuilder.create().text("Last line").style("-fx-font-weight:bold;").build());
			Tooltip tp = new Tooltip();
			tp.setGraphic(vb);
			
			setTooltip(tp);
		}
		public CustomTextField(String str){
			super(str);
			addListener();
		}
		private void addListener() {
			this.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean paramT2) {
					if(!paramT2){
						if(getText()!=null && getText().length()>0){
							setText(getText().trim());
						}
					}
				}
			});
			
		}
	}
}

class MyObject{
	private SimpleStringProperty name = new SimpleStringProperty("");

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getName(){
		return this.name.get();
	}
}
