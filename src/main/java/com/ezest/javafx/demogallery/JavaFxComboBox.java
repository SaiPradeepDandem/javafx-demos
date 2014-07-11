package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

public class JavaFxComboBox extends Application {

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
		ObservableList<String> a = FXCollections.observableArrayList();
		for(int i=0;i<99;i++){
			a.add("Item "+i);
		}
		ComboBox<String> cb = new ComboBox<String>();
		cb.setItems(a);
		
		final ComboBox<String> minitueChoice = new ComboBox<String>();
        minitueChoice.autosize();
        minitueChoice.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> paramP) {
				paramP.setMaxHeight(146); // To show list view till 5 options
				return new ListCell<String>(){;
					@Override
					protected void updateItem(String paramT,boolean paramBoolean) {
						super.updateItem(paramT, paramBoolean);
						if(!isEmpty()){
							setGraphic(new Label(paramT));
						}
					}
				};
			}
		});
        minitueChoice.setItems(FXCollections.observableArrayList(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"));
        minitueChoice.getSelectionModel().selectFirst();
        
		root.getChildren().addAll(cb,minitueChoice);
	}

	private void configureStage(){
		stage.setTitle("ComboBox");
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

