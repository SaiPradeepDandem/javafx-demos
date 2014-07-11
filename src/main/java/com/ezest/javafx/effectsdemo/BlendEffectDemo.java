package com.ezest.javafx.effectsdemo;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlendEffectDemo extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Group root  = new Group();
		Scene scene = new Scene(root, 500, 500, Color.BISQUE);
		
		Rectangle r = new Rectangle(50, 50, 100, 50); // x, y, width, height
		r.setFill(Color.GREY);
		Circle c = new Circle(r.getX(), r.getY(), 30, Color.GREEN);
		
		mode_ADD(root, r, c);
		mode_BLUE(root, r, c);
		
		root.getChildren().add( BloomEffectDemo.bloom());
		
		stage.setTitle("JavaFx Blend Effect Demo");
		stage.setScene(scene);
		stage.show();
	}
	
	private void mode_ADD(Group root,Rectangle r,Circle c){
		Group child  = new Group();
		Text txt = new Text("ADD");
		txt.setFill(Color.BLACK);
		child.getChildren().add(txt);
		child.getChildren().add(r);
		child.getChildren().add(c);
		child.setBlendMode(BlendMode.ADD);
		
		root.getChildren().add(child);
	}
	
	private void mode_BLUE(Group root,Rectangle r,Circle c){
		Group child  = new Group();
		Text txt = new Text("BLUE");
		txt.setFill(Color.BLACK);
		child.getChildren().add(txt);
		child.getChildren().add(r);
		child.getChildren().add(c);
		child.setBlendMode(BlendMode.MULTIPLY);
		
		root.getChildren().add(child);
	}

}
