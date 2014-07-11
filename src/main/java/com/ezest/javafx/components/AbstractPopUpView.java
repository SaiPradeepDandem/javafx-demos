package com.ezest.javafx.components;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class AbstractPopUpView {
	private Stage stage;
	private Scene scene;
	private StackPane root;
	
	private WindowResizeButton windowResizeButton;
	private WindowButtons buttonsPane;
	
	public AbstractPopUpView(Stage parentStage, Double width, Double height){
		this.stage = new Stage();
		windowResizeButton = new WindowResizeButton(stage, 500,500);
		buttonsPane = new WindowButtons(stage);
		
		this.root = new StackPane(){
			 @Override protected void layoutChildren() {
	                super.layoutChildren();
	                windowResizeButton.autosize();
	                windowResizeButton.setLayoutX(getWidth()-(windowResizeButton.getLayoutBounds().getWidth()+2));
	                windowResizeButton.setLayoutY(getHeight()-(windowResizeButton.getLayoutBounds().getHeight()+2));
	                buttonsPane.autosize();
	                buttonsPane.setLayoutX(getWidth()-(buttonsPane.getLayoutBounds().getWidth()+8));
	                buttonsPane.setLayoutY(8);
	            }
		};
		root.getStyleClass().add("mainStage");
		root.autosize();
		//root.getChildren().add( new Text("Window under progress...") );
		
		// Creating a scene with the specified size.
	    this.scene = new Scene(root, width, height, Color.web("#FFFFFF"));
	    //scene.getStylesheets().add("/css/emr_styles.css");
		stage.setScene(scene);
		//stage.initStyle(StageStyle.TRANSPARENT);
		stage.initOwner( parentStage);
		stage.initModality(Modality.APPLICATION_MODAL);
		//stage.show();
	}
	public void show(){
		stage.show();
	}

	/**
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}
	/**
	 * @return the root
	 */
	public StackPane getRoot() {
		return root;
	}
	/**
	 * @return the scene
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * Method to set the title property for the window.
	 * @param title - Title of the pop up
	 */
	protected void setPopUpTitle(String title){
		this.stage.setTitle(title);
	}
	
}
