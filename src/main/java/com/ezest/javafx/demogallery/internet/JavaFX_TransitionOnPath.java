package com.ezest.javafx.demogallery.internet;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.StrokeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
 
/**
 *
 * @web java-buddy.blogspot.com
 */
public class JavaFX_TransitionOnPath extends Application {
     
    PathTransition pathTransition;
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("java-buddy.blogspot.com");
        Group root = new Group();
        Scene scene = new Scene(root, 400, 300, Color.WHITE);
         
        final Image image1 = new Image(getClass().getResourceAsStream("/images/delete.png"));
        final ImageView imageView = new ImageView();
        imageView.setImage(image1);
         
        final Path path = new Path();
        path.setStrokeWidth(1);
        path.setStroke(Color.BLACK);
         
        //Mouse button pressed - clear path and start from the current X, Y.
        scene.onMousePressedProperty().set(new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                //path.getElements().clear();
                path.getElements().add(new MoveTo(event.getX(), event.getY()));
            }
        });
 
        //Mouse dragged - add current point.
        scene.onMouseDraggedProperty().set(new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                path.getElements().add(new LineTo(event.getX(), event.getY()));
            }
        });
                 
        //Mouse button released,  finish path.
        scene.onMouseReleasedProperty().set(new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
                pathTransition = PathTransitionBuilder.create()
                        .node(imageView)
                        .path(path)
                        .duration(Duration.millis(5000))
                        .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                        .cycleCount(1)
                        .build();
 
                pathTransition.play();
                
                FadeTransition ft = new FadeTransition(Duration.millis(5000), path);
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.play();
                
                StrokeTransition sT = new StrokeTransition(Duration.millis(5000), path, Color.RED, Color.BLUE);
                sT.play();
            }
        });
 
        root.getChildren().add(imageView);
        root.getChildren().add(path);
        primaryStage.setScene(scene);
        primaryStage.show();
 
    }
}
