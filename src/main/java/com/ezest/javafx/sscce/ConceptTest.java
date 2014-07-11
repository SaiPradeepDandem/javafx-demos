package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConceptTest extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    
    public void start(Stage primaryStage) {

        
        primaryStage.setTitle("Horizontal Scroll Test");
        final Label header = new Label("This is a horizontal scroll test");
        final Label lab1 = new Label("abcdefghijklmnopqrstuvwxyz0" + 
                "abcdefghijklmnopqrstuvwxyz1" + "abcdefghijklmnopqrstuvwxyz2" + 
                "abcdefghijklmnopqrstuvwxyz3" + "abcdefghijklmnopqrstuvwxyz4" + 
                "abcdefghijklmnopqrstuvwxyz5");
        final Label lab2 = new Label("abcdefghijklmnopqrstuvwxyz6" + 
                "abcdefghijklmnopqrstuvwxyz7" + "abcdefghijklmnopqrstuvwxyz8" + 
                "abcdefghijklmnopqrstuvwxyz9");

               
        final HBox hb = new HBox();
        hb.getChildren().addAll(lab1, lab2);

               
        final VBox vb = new VBox(10.0);
        vb.getChildren().addAll(header, hb);

              
        final ScrollPane sp = new ScrollPane(){
	        @Override
	        protected void layoutChildren() {
	        	super.layoutChildren();
	        	double hmin, hmax;
	            hmin = getHmin();
	            hmax = getHmax();
	            setHvalue(0.5*(hmin + hmax));
	        }
        };
        sp.setContent(vb);
        sp.setMinWidth(600.0);
        sp.setMinHeight(100.0);

        Group root = new Group();
        Scene scene = new Scene(root, 600, 150);

        
        root.getChildren().add(sp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

