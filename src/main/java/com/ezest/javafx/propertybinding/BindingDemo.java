package com.ezest.javafx.propertybinding;



import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BindingDemo extends Application {

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
		
		
		
		final Label lbl1 = new Label();
		final TextField txt1 = new TextField();
		Button btn1 = new Button("Add");
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				lbl1.setText(txt1.getText());
			}
		});
		HBox hb1 = HBoxBuilder.create().children(new Label("Text for label 1 : "),txt1, btn1).build();
		
		final Label lbl2 = new Label();
		final TextField txt2 = new TextField();
		Button btn2 = new Button("Add");
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				lbl2.setText(txt2.getText());
			}
		});
		HBox hb2 = HBoxBuilder.create().children(new Label("Text for label 2 : "),txt2, btn2).build();
		
		final HBox hb3 = HBoxBuilder.create().children(lbl1,lbl2).build();
		
		/*lbl1.visibleProperty().bind( new BooleanBinding() {
			{
				super.bind(lbl2.textProperty());
			}
			@Override
			protected boolean computeValue() {
				if(lbl2.getText()!=null && lbl2.getText().length()>0){
					hb3.getChildren().remove(lbl1);
					return false;
				}else{
					hb3.getChildren().add(lbl1);
					return true;
				}
			}
		});*/
		
		/*
		text.textProperty().bind( new When(image.errorProperty()).then(new SimpleStringProperty("Load Failed")).otherwise( (Bindings.format("Loading: %s", image.progressProperty())) ));
        
		text.visibleProperty().bind(image.errorProperty().or(image.progressProperty().isNotEqualTo(new SimpleIntegerProperty(100))));
		*/
		final Label lbl3 = LabelBuilder.create().style("-fx-text-fill:red;").build();
		lbl1.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0,String arg1, String arg2) {
				lbl3.setText(arg2);
			}
		});
		lbl2.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0,String arg1, String arg2) {
				lbl3.setText(arg2);
			}
		});
		
		this.sp = new StackPane();
		sp.setPrefSize(100, 100);
		sp.setStyle("-fx-background-color:red;");
			
		this.gp = new Group();
		this.gp.getChildren().add(sp);
		
		Button btn = new Button("Change");
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				changeMe();
			}
		});
		HBox hb4 = HBoxBuilder.create().children(this.gp,btn).build();
		root.getChildren().addAll(hb1,hb2,lbl3,hb4);
		
	}
	private int i =0;
	private StackPane sp;
	private  Group gp;
	
	private void changeMe(){
		StackPane sp1 = new StackPane();
		sp1.setPrefSize(100, 100);
		sp1.setStyle("-fx-background-color:"+(i%2==0?"red":(i%3==0?"green":"blue"))+";");
		gp.getChildren().clear();
		gp.getChildren().add(sp1);
		gp.requestLayout();
		i++;
	}

	private void configureStage(){
		stage.setTitle("Binding Demo");
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

}


