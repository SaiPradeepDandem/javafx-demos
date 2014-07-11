package com.ezest.javafx.demogallery;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StageBlinkDemo extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle("i'll blink");
        stage.setScene(new Scene(new Group(new Text(25,25,"blink-blink"))));
        stage.show();

        TimelineBuilder.create().keyFrames(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                stage.toFront();
            }
        })).cycleCount(Timeline.INDEFINITE).build().play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
