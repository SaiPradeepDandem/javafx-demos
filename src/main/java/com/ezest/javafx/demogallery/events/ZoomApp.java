package com.ezest.javafx.demogallery.events;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Demo of a challenge I have with zooming inside a {@code ScrollPane}.
 * <br>
 * I am running JavaFx 2.2 on a Mac. {@code java -version} yields:
 * <pre>
 * java version "1.7.0_09"
 * Java(TM) SE Runtime Environment (build 1.7.0_09-b05)
 * Java HotSpot(TM) 64-Bit Server VM (build 23.5-b02, mixed mode)
 * </pre>
 * 6 rectangles are drawn, and can be zoomed in and out using either
 * <pre>
 * Ctrl + Mouse Wheel
 * or Ctrl + 2 fingers on the pad.
 * </pre>
 * It reproduces a problem I experience inside an application I am writing.
 * If you magnify to {@link #MAX_SCALE}, an interesting problem occurs when you try to zoom back to {@link #MIN_SCALE}. In the beginning
 * you will see that the {@code scrollPane} scrolls and consumes the {@code ScrollEvent} until we have scrolled to the bottom of the window.
 * Once the bottom of the window is reached, it behaves as expected (or at least as I was expecting).
 *
 * @author Skjalg Bjørndal
 * @since 2012.11.05
 */
public class ZoomApp extends Application {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private static final double MAX_SCALE = 2.5d;
    private static final double MIN_SCALE = .5d;

    private class ZoomHandler implements EventHandler<ScrollEvent> {

        private Node nodeToZoom;

        private ZoomHandler(Node nodeToZoom) {
            this.nodeToZoom = nodeToZoom;
        }

        @Override
        public void handle(ScrollEvent scrollEvent) {
            if (scrollEvent.isControlDown()) {
                final double scale = calculateScale(scrollEvent);
                nodeToZoom.setScaleX(scale);
                nodeToZoom.setScaleY(scale);
                scrollEvent.consume();
            }
        }

        private double calculateScale(ScrollEvent scrollEvent) {
            double scale = nodeToZoom.getScaleX() + scrollEvent.getDeltaY() / 100;

            if (scale <= MIN_SCALE) {
                scale = MIN_SCALE;
            } else if (scale >= MAX_SCALE) {
                scale = MAX_SCALE;
            }
            return scale;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        final Group innerGroup = createSixRectangles();
        final Group outerGroup = new Group(innerGroup);

        final ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(outerGroup);
        scrollPane.setOnScroll(new ZoomHandler(innerGroup));

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(scrollPane);

        Scene scene = SceneBuilder.create()
                .width(WINDOW_WIDTH)
                .height(WINDOW_HEIGHT)
                .root(stackPane)
                .build();

        stage.setScene(scene);
        stage.show();
    }

    private Group createSixRectangles() {
        return new Group(
                createRectangle(0, 0), createRectangle(110, 0), createRectangle(220, 0),
                createRectangle(0, 110), createRectangle(110, 110), createRectangle(220, 110),
                createRectangle(0, 220), createRectangle(110, 220), createRectangle(220, 220)
        );
    }

    private Rectangle createRectangle(int x, int y) {
        Rectangle rectangle = new Rectangle(x, y, 100, 100);
        rectangle.setStroke(Color.ORANGERED);
        rectangle.setFill(Color.ORANGE);
        rectangle.setStrokeWidth(3d);
        return rectangle;
    }
}
