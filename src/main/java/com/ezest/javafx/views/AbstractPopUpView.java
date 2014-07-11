package com.ezest.javafx.views;

import com.ezest.javafx.components.WindowButtons;
import com.ezest.javafx.components.WindowResizeButton;


import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class AbstractPopUpView {
	//private EMRView emrView;
	private Stage stage;
	private Scene scene;
	private StackPane root;
	
	public AbstractPopUpView(Stage parentStage, /*EMRView emrView,*/ Double width, Double height){
		this.stage = new Stage();
		this.stage.initStyle(StageStyle.TRANSPARENT);
		
		this.root = new StackPane();
		root.getStyleClass().add("popUp");
		root.autosize();
		
		 
		TitledPane titledPane = new TitledPane();
		titledPane.autosize();
		titledPane.setExpanded(true);
		titledPane.setCollapsible(false);
		
		WindowButtons buttonsPane = new WindowButtons(this.stage);
		root.getChildren().add(buttonsPane);
		HBox.setHgrow(root, Priority.ALWAYS);
		root.getChildren().add( new Text("Window under progress...") );
		
		// Creating a scene with the specified size.
	    this.scene = new Scene(root, width, height, Color.TRANSPARENT);
		scene.getStylesheets().add("/styles/sample.css");
		stage.setScene(scene);
		stage.initOwner( parentStage);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}
	/**
	 * @return the emrView
	 */
	/*public EMRView getEmrView() {
		return emrView;
	}*/
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
	
	public static void main(String[] args) {
		String str = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB7.1; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3)";
		if (str.indexOf("MSIE") > -1){
			String str1 = str.substring( str.indexOf("MSIE")) ;
			String str2 = str1.substring(5, str1.indexOf(".")) ;
			System.out.println(str2);
		}
	}
}
