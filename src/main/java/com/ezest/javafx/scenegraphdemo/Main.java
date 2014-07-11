package com.ezest.javafx.scenegraphdemo;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		Group root  = new Group();
		Scene scene = new Scene(root, 500, 500, Color.BISQUE);
		
		Rectangle r = new Rectangle(50, 25, 400, 50); // x, y, width, height
		r.setFill(Color.GREY);
		r.setLayoutX(40);
		r.setScaleX(40);
		
		root.getChildren().add(r);
		
		Circle c1 = new Circle(r.getX(), r.getY(), 10, Color.BISQUE);
		root.getChildren().add(c1);
		
		Circle c2 = new Circle(r.getX()+r.getWidth(), r.getY(), 10, Color.BISQUE);
		root.getChildren().add(c2);
		
		Circle c3 = new Circle(r.getX(), r.getY()+r.getHeight(), 10, Color.BISQUE);
		root.getChildren().add(c3);
		
		Circle c4 = new Circle(r.getX()+r.getWidth(), r.getY()+r.getHeight(), 10, Color.BISQUE);
		root.getChildren().add(c4);
		
		Button btn = new Button("Show Window");
		btn.setTranslateX(200);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stage stg = new Stage();
				Group root1  = new Group();
				Scene scene1 = new Scene(root1, 250, 250, Color.AQUA);
				stg.setScene(scene1);
				stg.initOwner(stage);
				stg.initModality(Modality.APPLICATION_MODAL);
				stg.show();
				
			}
		});
		
		root.getChildren().add(btn);
		
		Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
	    stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(bounds.getWidth());
	    stage.setHeight(bounds.getHeight());
	    
		stage.setTitle("JavaFx Scene Graph Demo");
		stage.setScene(scene);
		stage.show();
		
	}

	
}
