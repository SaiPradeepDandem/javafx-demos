package com.ezest.javafx.layouts;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.ezest.javafx.common.FXTools;
import com.ezest.javafx.components.AbstractPopUpView;
import com.ezest.javafx.components.WindowButtons;
import com.ezest.javafx.components.WindowResizeButton;
import com.ezest.javafx.uicontrols.ChoiceBoxElement;

public class PopUpDemo extends Application {

	private WindowResizeButton windowResizeButton;
	private WindowButtons buttonsPane;
	@Override
	public void start(final Stage stage) throws Exception {
		windowResizeButton = new WindowResizeButton(stage, 500,500);
		buttonsPane = new WindowButtons(stage);
		
		StackPane root = new StackPane(){
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
		root.autosize();
		Scene scene = new Scene(root, 500, 500, Color.TRANSPARENT);
		scene.getStylesheets().add("styles/sample.css");
		root.getStyleClass().add("mainStage");
		
		DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.GRAY);
        //root.setEffect(ds);
		
        root.getChildren().add(buttonsPane);
		HBox.setHgrow(root, Priority.ALWAYS);
		
		Button btn = new Button("Pop Up");
		root.getChildren().add(btn);
		root.getChildren().add( windowResizeButton );
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				new MyPopView(stage);
			}
		});
		final Rectangle2D bounds = FXTools.getScreenBounds();
		stage.setWidth(bounds.getWidth()-300);
	    stage.setHeight(bounds.getHeight()-100);
	    stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("JavaFx Layout Demo");
		stage.setScene(scene);
		stage.show();
		
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	
}

class MyPopView extends AbstractPopUpView{

	public MyPopView(Stage stage){
		super(stage,800d,500d);
		super.setPopUpTitle("Title");
		final Stage par = super.getStage();
		final ChoiceBox cb = (ChoiceBox)ChoiceBoxElement.getNode();
		
		Button closeBtn = new Button();
        closeBtn.setId("window-close");
        closeBtn.setTranslateX(150);
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //Platform.exit();
            	//par.hide();
            	//par.close();
            	cb.hide();
            	par.hide();
            }
        });
        Group gp = new Group();
        
       // TODO : Add the form node.
		getRoot().getChildren().addAll(  cb,closeBtn,gp);
	}
}