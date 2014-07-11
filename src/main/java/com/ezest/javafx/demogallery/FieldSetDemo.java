package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FieldSetDemo extends Application {

	Stage stage;
	Scene scene;
	Group root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		StackPane sp = new StackPane();
		sp.setPrefSize(300, 300);
		
		
		FieldSet fs = new FieldSet("First Title");
		//fs.setStyleClassForBorder("fieldSetRed");
		//sp.getChildren().add(fs);
		
		HBox hb = new HBox();
		hb.setSpacing(3);
		final FieldSet fs1 = new FieldSet(hb);
		//fs1.setStyleClassForBorder("fieldSetRed");
		
		CheckBox cb =new CheckBox();
		cb.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean paramT2) {
				if(paramT2){
					//fs1.removeStyleClassForBorder("fieldSetGreen");
					//fs1.setStyleClassForBorder("fieldSetRed");
					fs1.getStyleClass().add("fieldSetRed");
					fs1.getStyleClass().remove("fieldSetGreen");
					
				}else{
					//fs1.removeStyleClassForBorder("fieldSetRed");
					//fs1.setStyleClassForBorder("fieldSetGreen");
					fs1.getStyleClass().add("fieldSetGreen");
					fs1.getStyleClass().remove("fieldSetRed");
				}
				
			}
		});
		hb.getChildren().addAll(cb, new Label("My Label"));
		sp.getChildren().add(fs1);
		
		
		root.getChildren().add(sp);
	}

	private void configureStage(){
		stage.setTitle("FieldSet Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new Group();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

	/**
	 * FieldSet Component.
	 * @author Sai.Dandem
	 *
	 */
	class FieldSet extends StackPane{
		private Label legend = new Label("");
		private Node legendNode;;
		private StackPane contentBox = new StackPane();
		private StackPane legendBox = new StackPane();
		
		public FieldSet(String legendStr){
			super();
			this.legend.setText(legendStr);
			legendBox.getChildren().add(legend);
			configureFieldSet();
		}
		
		public FieldSet(Node legendNode){
			super();
			this.legendNode = legendNode;
			legendBox.getChildren().add(legendNode);
			configureFieldSet();
		}
		
		private void configureFieldSet(){
			super.setPadding(new Insets(10,0,0,0));
			super.setAlignment(Pos.TOP_LEFT);
			super.getStyleClass().add("fieldSetDefault");
			
			contentBox.getStyleClass().add("fieldSet");
			contentBox.setAlignment(Pos.TOP_LEFT);
			contentBox.setPadding(new Insets(8, 2, 2, 2));
			
			legendBox.setPadding(new Insets(0, 5, 0, 5));
			
			Group gp = new Group();
			gp.setTranslateX(20);
			gp.setTranslateY(-8);
			gp.getChildren().add(legendBox);
			
			super.getChildren().addAll(contentBox, gp);
			setBackGroundColor("#FFFFFF");
			
			/* Adding listeners for styles. */
			getStyleClass().addListener(new ListChangeListener<String>() {
				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> paramChange) {
					System.out.println("-->"+getStyleClass());
					System.out.println(contentBox.getStyleClass());
					contentBox.getStyleClass().clear();
					contentBox.getStyleClass().addAll("fieldSet");
					for (String clazz : getStyleClass()) {
						if (!clazz.equals("fieldSetDefault")) {
							contentBox.getStyleClass().add(clazz);
						}
					}
				}
			});
		}
		
		/*
		 * PUBLIC METHODS
		 */
		
		public void setContent(Node content){
			contentBox.getChildren().add(content);
		}
		
		public void setBackGroundColor(String color){
			super.setStyle("-fx-background-color:"+color+";");
			contentBox.setStyle("-fx-background-color:"+color+";");
			legendBox.setStyle("-fx-background-color:"+color+";");
		}
		
		public void setStyleClassForBorder(String claz){
			contentBox.getStyleClass().add(claz);
		}
		public void removeStyleClassForBorder(String claz){
			contentBox.getStyleClass().remove(claz);
		}
		
	}
}


