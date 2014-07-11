package com.ezest.javafx.demogallery.internet;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Reference: http://blog.netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
 */
public class JavaFX_SpriteAnimation extends Application {

    private static final Image IMAGE = new Image(JavaFX_SpriteAnimation.class.getResourceAsStream("/images/The_Horse_in_Motion.jpg")); 

    private static final int COLUMNS  =   4;
    private static final int COUNT    =  10;
    private static final int OFFSET_X =  18;
    private static final int OFFSET_Y =  25;
    private static final int WIDTH    = 374;
    private static final int HEIGHT   = 243;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(600),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        primaryStage.setScene(new Scene(new Group(imageView)));
        primaryStage.show();
    }
}


class SpriteAnimation extends Transition {

    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    private int lastIndex;

    public SpriteAnimation(ImageView imageView, Duration duration, 
            			   int count,   int columns,
            			   int offsetX, int offsetY,
            			   int width,   int height) {
        this.imageView = imageView;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}