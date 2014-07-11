package com.ezest.javafx.demogallery;

import java.awt.event.MouseWheelEvent;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MouseEventDemo extends Application {

	Stage stage;
	Scene scene;
	Group root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		Image image = new Image(DetectiveGlass.class.getResourceAsStream("/images/scene.jpg"));
		final ImageView maskView = new ImageView(image);
		maskView.addEventFilter(ScrollEvent.SCROLL, new EventHandler(){
			@Override
			public void handle(Event paramT) {
				ScrollEvent event = (ScrollEvent) paramT;
				System.out.println("DeltaX : "+event.getDeltaX());
				System.out.println("DeltaY : "+event.getDeltaY());
				System.out.println("SceneX : "+event.getSceneX());
				System.out.println("SceneY : "+event.getSceneY());
				System.out.println("ScreenX : "+event.getScreenX());
				System.out.println("ScreenY : "+event.getScreenY());
				System.out.println("TextDeltaX : "+event.getTextDeltaX());
				System.out.println("TextDeltaY : "+event.getTextDeltaY());
				System.out.println("X : "+event.getX());
				System.out.println("Y : "+event.getY());
				System.out.println("TextDeltaXUnits().ordinal : "+event.getTextDeltaXUnits().ordinal());
				System.out.println("TextDeltaYUnits().ordinal : "+event.getTextDeltaYUnits().ordinal());
				System.out.println("***********************************************************************************");
				System.out.println("***********************************************************************************");
				 maskView.setScaleX(3);
			     // maskView.setScaleY(wr);
			}
	   });
		/*maskView.addEventHandler(MouseEvent.IMPL_MOUSE_WHEEL_ROTATED, new EventHandler<MouseEvent>() {
		   @Override
		   public void handle(MouseEvent event) {
		      double wr = event.impl_getWheelRotation();
		      System.out.println(wr);
		      maskView.setScaleX(wr);
		     // maskView.setScaleY(wr);
			}
		});*/

		root.getChildren().add(maskView);
	}

	private void configureStage(){
		stage.setTitle("XXXXXXXXXXXX");
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
		this.scene = new Scene(root, Color.LINEN){
			protected void processMouseWheelEvent(MouseWheelEvent e) {
			   System.out.println(e.getWheelRotation());
			}
		};
		scene.getStylesheets().add("styles/template.css");
		
	}

}

