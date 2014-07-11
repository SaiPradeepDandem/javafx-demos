package com.ezest.javafx.effectsdemo;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlurEffectDemo extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Group root  = new Group();
		Scene scene = new Scene(root, 500, 500, Color.BISQUE);
		
		root.getChildren().add(boxBlur());
		root.getChildren().add(motionBlur());
		root.getChildren().add(gaussianBlur());
		root.getChildren().add(BloomEffectDemo.bloom());
		
		stage.setTitle("JavaFx Blur Effect Demo");
		stage.setScene(scene);
		stage.show();
	}

	private Node boxBlur() {
        Text t = new Text();
        t.setText("Blurry Text!");
        t.setFill(Color.RED);
        t.setFont(Font.font("null", FontWeight.BOLD, 36));
        t.setX(10);
        t.setY(40);
 
        BoxBlur bb = new BoxBlur();
        bb.setWidth(5);
        bb.setHeight(5);
        bb.setIterations(3);
 
        t.setEffect(bb);
        //t.setTranslateX(300);
        //t.setTranslateY(100);
 
        return t;
    }
	
	 private Node motionBlur() {
	        Text t = new Text();
	        t.setX(10.0f);
	        t.setY(150.0f);
	        t.setText("Motion Blur");
	        t.setFill(Color.RED);
	        t.setFont(Font.font("null", FontWeight.BOLD, 60));
	 
	        MotionBlur mb = new MotionBlur();
	        mb.setRadius(15.0f);
	        mb.setAngle(45.0f);
	 
	        t.setEffect(mb);
	 
	        //t.setTranslateX(300);
	       // t.setTranslateY(150);
	 
	        return t;
	    }
	 
	 private Node gaussianBlur() {
	        Text t2 = new Text();
	        t2.setX(10.0f);
	        t2.setY(250.0f);
	        t2.setCache(true);
	        t2.setText("Gaussian Blur");
	        t2.setFill(Color.RED);
	        t2.setFont(Font.font("null", FontWeight.BOLD, 36));
	        
	        DropShadow shadow = new DropShadow();
	        shadow.setOffsetY(3.0);
	        shadow.setOffsetX(3.0);
	        shadow.setColor(Color.GRAY);
	        shadow.setInput(new GaussianBlur());
	        
	        Reflection rf = new Reflection();
	        rf.setFraction(1.5);
	        rf.setInput(shadow);
	        
	        t2.setEffect(rf);
	        return t2;
	    }
}
