package com.ezest.javafx.demogallery;

import com.ezest.javafx.uicontrols.ScrollBarElement;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Narayan G.M
 */
public class DetectiveGlass extends Application {

    public static void main(String[] args) {
        Application.launch(DetectiveGlass.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Detective Glass");
        Group root = new Group();
        Scene scene = new Scene(root, 720, 470,Color.BLACK);

        //ImageView
        ImageView maskView = new ImageView();
        maskView.setCursor(Cursor.NONE);
        //Image is loaded
        Image image = new Image(DetectiveGlass.class.getResourceAsStream("/images/scene.jpg"));
        
        maskView.setImage(image);
        //Mask Shape
        final Circle glass = new Circle(100,100,100);
        maskView.setClip(glass);

        //adding to the root's children
        root.getChildren().add(maskView);

        //MouseMoved Event on Scene
        scene.setOnMouseMoved(new EventHandler<MouseEvent>(){

        	@Override
			public void handle(MouseEvent event) {
                //Setting X and Y position of mask shape
                glass.setCenterX(event.getX());
                glass.setCenterY(event.getY());
            }

		});

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

