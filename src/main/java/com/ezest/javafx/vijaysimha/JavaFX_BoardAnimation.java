package com.ezest.javafx.vijaysimha;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFX_BoardAnimation extends Application {

    private static final Image IMAGE = new Image(JavaFX_BoardAnimation.class.getResourceAsStream("/images/casino/ballinzoom2.png")); 

    private static final int COLUMNS  =   10;
    private static final int COUNT    =  10;
    private static final int WIDTH    = 147;//252;
    private static final int HEIGHT   = 110;//140;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Board In Action");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(0, 0, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(600),
                COUNT, COLUMNS,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(3);
       // animation.play();

        final Animation animation2 = new SpriteAnimation(
                imageView,
                Duration.millis(1200),
                COUNT, COLUMNS,
                WIDTH, HEIGHT
        );
        animation2.setCycleCount(3);
        //animation2.play();
        
        final Animation animation3 = new SpriteAnimation(
                imageView,
                Duration.millis(1800),
                COUNT, COLUMNS,
                WIDTH, HEIGHT
        );
        animation3.setCycleCount(3);
         
        final Animation animation4 = new SpriteAnimation(
                imageView,
                Duration.millis(2400),
                COUNT, COLUMNS,
                WIDTH, HEIGHT
        );
        animation4.setCycleCount(3);
         
        final SequentialTransition sequentialTransition = SequentialTransitionBuilder.create()
		.children(animation, animation2, animation3, animation4)
		.cycleCount(1)
		.autoReverse(true)
		.build();
        sequentialTransition.play();
        
        primaryStage.setScene(new Scene(new Group(imageView)));
        primaryStage.show();
    }
}


class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int width;
    private final int height;

    private int lastIndex;

    public SpriteAnimation(ImageView imageView, Duration duration, 
            			   int count,   int columns,
            			   int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {
    	final int index = Math.min((int) Math.floor(k * count), count - 1);
    	if (index != lastIndex) {
            final int x = (index % columns) * width  ;
            final int y = (index / columns) * height ;
            //System.out.println(index + " : "+x +" : "+y);
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}