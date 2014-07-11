package com.ezest.javafx.layouts;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LetterTemplateLayoutDemo extends Application {
	StackPane root;
	ScrollPane scrollPane;
	VBox letterLayout;
	List<String> lineList;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new StackPane();
		scrollPane = new ScrollPane();
		scrollPane.setContent(letterLayout);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToHeight(true);
		
		Scene scene = new Scene(root, 1124, 650);
		letterLayout  = new VBox();
		root.getChildren().add( scrollPane );
		scrollPane.setContent(letterLayout);
		
		stage.setTitle("JavaFx Layout Demo");
		stage.setScene(scene);
		
		readLetter();
		renderLetter();
		stage.show();
	}

	private void renderLetter() {
		for (String line : lineList) {
			HBox hb = new HBox();
			hb.setStyle("-fx-border-color:red;-fx-border-width:1px;");
			hb.setMinHeight(24);
			
			letterLayout.getChildren().add(hb);
		}
	}

	private void readLetter() {
		lineList = new ArrayList<String>();
		try{
			  FileInputStream fstream = new FileInputStream("E:/Projects/JSF/workspace/JavaFX2APP/src/main/java/com/ezest/javafx/layouts/lettertemplate.txt");
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  while ((strLine = br.readLine()) != null)   {
				  lineList.add(strLine);
			  }
			  in.close();
			  
		}catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
		}
	}

}
