package com.ezest.javafx.components.javafxcombobox;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class JavaFXComboBoxAutoSuggestDemo extends Application {

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
		
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setAlignment(Pos.CENTER);
		root.getChildren().add(hb);
		// Logic starts
		final AutoSuggestComboBox<String> combo = new AutoSuggestComboBox<String>();
		combo.setItemsToCombo(getlist());
		combo.getComboBox().setPrefWidth(150);
		combo.getComboBox().setPrefHeight(50);
		combo.selectedItemProperty().set("Catwoman");
		final String pmpt ="Hello world";
		ComboBox<String> combo2 = new ComboBox<String>(){
			@Override
			protected void layoutChildren() {
				super.layoutChildren();
				Text txt = (Text)lookup(".text");
				
				if(txt!=null && txt.getText()!=null){
					if(txt.getText().equals(pmpt)){
						txt.getStyleClass().add("combo-prompt-text");
					}else{
						txt.getStyleClass().remove("combo-prompt-text");
					}
				}
			}
		};
		//combo2.setEditable(true);
		combo2.setItems(getlist());
		combo2.setPromptText("Hello world");
		
		
		Button btn = new Button("dm");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				System.out.println(combo.selectedItemProperty().get());
				for (String file : scene.getStylesheets()) {
					System.out.println(file);
				}
			}
		});
		hb.getChildren().addAll(combo, combo2, btn );
	}

	private ObservableList<String> getlist(){
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("Apple");
		list.add("Batman");
		list.add("Catwoman");
		list.add("Dogaurd");
		list.add("Elepahat");
		list.add("Fox");
		list.add("Gorilla");
		return list;
	}
	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	    
	    stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean focused) {
				if(focused){
					System.out.println("I am focused");
				}
			}
		});
	    ScenicView.show(this.scene);
	}
	
	private void configureScene(){
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setBottom(getBottom());
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

	private Node getBottom() {
		StackPane sp = new StackPane();
		sp.setMinHeight(25);
		sp.setAlignment(Pos.TOP_RIGHT);
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/mglass.gif")));
		image.setCursor(Cursor.HAND);
		image.setTranslateX(-5);
		image.setTranslateY(3);
		image.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				ScenicView.show(scene);
			}
		});
		sp.getChildren().addAll(new Separator(),image);
		return sp;
	}

}


