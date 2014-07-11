package com.ezest.javafx.demogallery.controls;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.sun.javafx.scene.web.skin.HTMLEditorSkin;

public class HTMLEditorDemo extends Application {

	Stage stage;
	Scene scene;
	Group root;
	private static ResourceBundle bundleProperty = null;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		VBox vb = new VBox();
		vb.setSpacing(15);
		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(550, 550);
		
		//Locale.setDefault(new Locale("nl","BE"));
		
		final HTMLEditor htmlEditor = new HTMLEditor();
		// Removing the paragraph combobox.
		ToolBar bar = (ToolBar)htmlEditor.lookup(".bottom-toolbar");
		ComboBox paragraph = (ComboBox)bar.getItems().get(0);
		bar.getItems().remove(paragraph);
		
		// Adding custom node.
		Button btn = new Button("My Button");
		bar.getItems().add(btn);
		
		final SimpleObjectProperty<ToggleButton> italicBtn = new SimpleObjectProperty<ToggleButton>();
		final SimpleObjectProperty<ToggleButton> underlineBtn = new SimpleObjectProperty<ToggleButton>();
		for (Node node : bar.getItems()) {
			if(node instanceof ToggleButton && node.getUserData().equals("italic")){
				italicBtn.set ( (ToggleButton)node);
			}else if(node instanceof ToggleButton && node.getUserData().equals("underline")){
				underlineBtn.set ( (ToggleButton)node);
			}
		}
		
		htmlEditor.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				final KeyCombination icombination=new KeyCodeCombination(KeyCode.I,KeyCombination.CONTROL_DOWN);
				final KeyCombination ucombination=new KeyCodeCombination(KeyCode.U,KeyCombination.CONTROL_DOWN);
				
				if(icombination.match(event)){
					/*try {
						Field privateItalicButtonField = HTMLEditorSkin.class.getDeclaredField("italicButton");
						privateItalicButtonField.setAccessible(true);
						htmlEditor.requestFocus();
						ToggleButton italicButton = (ToggleButton) privateItalicButtonField.get(htmlEditor.getSkin());
						italicButton.fire();
					} catch (IllegalAccessException e){
						
					} catch (NoSuchFieldException e) {
						System.out.println(e);
					}*/
					/*ToolBar bar = (ToolBar)htmlEditor.lookup(".bottom-toolbar");
					for (Node node : bar.getItems()) {
						if(node instanceof ToggleButton && node.getUserData().equals("italic")){
							((ToggleButton)node).fire();
						}
					}*/
					italicBtn.get().fire();
				}else if(ucombination.match(event)){
					/*try {
						Field privateUnderLineButtonField = HTMLEditorSkin.class.getDeclaredField("underlineButton");
						privateUnderLineButtonField.setAccessible(true);
						htmlEditor.requestFocus();
						ToggleButton underlineButton = (ToggleButton) privateUnderLineButtonField.get(htmlEditor.getSkin());
						underlineButton.fire();
					} catch (IllegalAccessException e){
						
					} catch (NoSuchFieldException e) {
						System.out.println(e);
					}*/
					/*ToolBar bar = (ToolBar)htmlEditor.lookup(".bottom-toolbar");
					for (Node node : bar.getItems()) {
						if(node instanceof ToggleButton && node.getUserData().equals("underline")){
							((ToggleButton)node).fire();
						}
					}*/
					underlineBtn.get().fire();
				}
			}
		});
		
		sp.setContent(htmlEditor);
		
		
		Button btn1 = new PushButton();
		btn1.setText("500");
		btn1.setOnAction(setOnAction(htmlEditor, 500));
		Button btn2 = new Button("600");
		btn2.setOnAction(setOnAction(htmlEditor, 600));
		Button btn3 = new Button("800");
		btn3.setOnAction(setOnAction(htmlEditor, 800));
		
		
		vb.getChildren().addAll(btn1, btn2, btn3, sp);
		root.getChildren().add(vb);
	}

	public EventHandler<ActionEvent> setOnAction(final HTMLEditor editor, final double width){
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				editor.setPrefWidth(width);
			}
		};
	}
	
	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
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
	}

}
class PushButton extends Button
{
	public PushButton()
	{
		
		/*
		 * Mouse Pressed Event
		 */
		final EventHandler<MouseEvent> mousePressedEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("MousePressed");
			};
		};
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedEvent);

		/*
		 * Mouse Released Event
		 */
		this.addEventHandler(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				System.out.println("MouseReleased");
				// Make a call to the mouse pressed event
				mousePressedEvent.handle(e);
			};
		});
	}
}


