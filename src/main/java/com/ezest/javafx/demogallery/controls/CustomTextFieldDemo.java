package com.ezest.javafx.demogallery.controls;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class CustomTextFieldDemo extends Application {

	Stage stage;
	Scene scene;
	Group root;
	
	VBox vb;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		vb = new VBox();
		vb.setSpacing(8);
		configureScene();
		configureStage();
		configureScroller();
		root.getChildren().add(vb);
		ScenicView.show(root);
	}

	private void configureScroller() {
		CustomTextField field = new CustomTextField();
		field.setType(CustomTextField.TextFieldType.POSITIVE_INTEGER);
		field.setPromptText("hello");
		field.setMaxCharLength(5);
		HBox hb1 = HBoxBuilder.create().spacing(10).children(new Label("INTEGER_ONLY   : ") 
                                                             ,CustomTextFieldBuilder.create()
                                                             		.type(CustomTextField.TextFieldType.POSITIVE_INTEGER)
                                                             		.maxCharLength(5).build()
                                                     ).build();
		
		CustomPromptTextField promptField = new CustomPromptTextField();
		promptField.setPromptText("Enter value");
		
		vb.getChildren().addAll(HBoxBuilder.create().spacing(10)
				                           .children(new Label("POSITIVE_INTEGER   : ") 
                                                     ,CustomTextFieldBuilder.create()
 		                                                 .type(CustomTextField.TextFieldType.POSITIVE_INTEGER)
 		                                                 .maxCharLength(5).build() ).build()
 		                       
 		                       ,HBoxBuilder.create().spacing(10)
				                           .children(new Label("ALPHABET   : ") 
			                               ,CustomTextFieldBuilder.create()
			                                    .type(CustomTextField.TextFieldType.ALPHABET)
			                                    .maxCharLength(5).build() ).build()
	                           
			                   ,HBoxBuilder.create().spacing(10)
				                           .children(new Label("Double   : ") 
			                               ,CustomTextFieldBuilder.create()
			                                    .type(CustomTextField.TextFieldType.DOUBLE)
			                                    .maxCharLength(13).build() ).build()
			                   ,promptField
			                   ,TextFieldBuilder.create().promptText("Enter value").build()
		 		                       
				);
		
	}

	private void configureStage(){
		stage.setTitle("Custom Text Field Demo");
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

class CustomPromptTextField extends StackPane{
	private TextField textField = new TextField();
	private TextField promptField = TextFieldBuilder.create().style("-fx-font-style:italic;").build();
	
	private ChangeListener<String> textChangeListener = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> arg0,
				String arg1, String text) {
			if(text!=null  && text.length()>0){
				textField.setVisible(true);
				promptField.setVisible(false);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						textField.requestFocus();
						textField.positionCaret(textField.getText().length());
					}
				});
			}else{
				textField.setVisible(false);
				promptField.setVisible(true);
			}
		}
	};
	
	public CustomPromptTextField(){
		super();
		configure();
	}
	
	public CustomPromptTextField(String text){
		super();
		this.textField.setText(text);
		this.promptField.setText(text);
		configure();
	}
	
	private void configure() {
		this.textField.textProperty().bindBidirectional(this.promptField.textProperty());
		this.textField.textProperty().addListener(textChangeListener);
		this.promptField.textProperty().addListener(textChangeListener);
		getChildren().addAll(this.textField, this.promptField);
	}
	
	public void setPromptText(String text){
		this.textField.setPromptText(text);
		this.promptField.setPromptText(text);
	}
	
}


