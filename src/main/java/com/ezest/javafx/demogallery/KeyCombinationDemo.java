package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KeyCombinationDemo extends Application {
	
	
	Stage stage;
	Scene scene;
	StackPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		root = new StackPane();
		root.getChildren().add(TextFieldBuilder.create().maxHeight(30).maxWidth(200).build());
		this.scene = new Scene(root, Color.LINEN);
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(600);
	    stage.setHeight(600);
	    stage.setScene(this.scene);
	    stage.show();
		final KeyCombination keyComb1=new KeyCodeCombination(KeyCode.I,KeyCombination.CONTROL_DOWN);
		final KeyCharacterCombination keyComb2 = new KeyCharacterCombination("M",KeyCombination.CONTROL_DOWN);
		
		final String keyCombination1 = "_ALT_E_O";
		final String keyCombination2 = "_ALT_E_P";
		final String keyCombination3 = "_CONTROL_H";
		final StringBuilder key = new StringBuilder();
		
		this.scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String codeStr = event.getCode().toString();
				System.out.println(codeStr);
				if(!key.toString().endsWith("_"+codeStr)){
					key.append("_"+codeStr);
				}
			}
		});
		
		this.scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(key.length()>0){
					if(key.toString().equals(keyCombination1)){
						System.out.println("Key Combination 1 pressed");
						
					}else if(key.toString().equals(keyCombination2)){
						System.out.println("Key Combination 2 pressed");
						
					}else if(key.toString().equals(keyCombination3)){
						System.out.println("Key Combination 3 pressed");
					}
					key.setLength(0);
				}
			}
		});
	}
}