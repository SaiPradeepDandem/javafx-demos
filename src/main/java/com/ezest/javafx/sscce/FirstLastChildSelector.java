package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FirstLastChildSelector extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    
    public void start(Stage primaryStage) {

        
        primaryStage.setTitle("List View Selection");
        
        final ListView<String> list = new ListView<String>();
        list.setItems(getList());
        list.setPrefWidth(150);
        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> paramP) {
				return new ListCell<String>(){
					@Override
					protected void updateItem(String paramT, boolean paramBoolean) {
						super.updateItem(paramT, paramBoolean);
						if(!isEmpty()){
							super.setGraphic(new Label(paramT));
							// Styling the first and last cells.
							if(getIndex()==0 || getIndex()==(list.getItems().size()-1)){
								this.getStyleClass().add("custom-cell");
							}
						}
					}
				};
			}
		});
        
        
        Group root = new Group();
        Scene scene = new Scene(root, 600, 150);
        scene.getStylesheets().add("styles/template.css");
        
        root.getChildren().add(list);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


	private ObservableList<String> getList() {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("One");
		list.add("Two");
		list.add("Three");
		list.add("Four");
		return list;
	}
}


