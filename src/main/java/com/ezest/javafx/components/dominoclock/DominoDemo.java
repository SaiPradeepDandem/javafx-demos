package com.ezest.javafx.components.dominoclock;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Calendar;


/**
 * Created by
 * User: hansolo
 * Date: 03.07.12
 * Time: 17:24
 */
public class DominoDemo extends Application {
    private Domino         hour;
    private Domino         minLeft;
    private Domino         minRight;
    private Domino         secLeft;
    private Domino         secRight;
    private long           lastTimerCall = System.nanoTime();
    private AnimationTimer timer         = new AnimationTimer() {
        @Override public void handle(long l) {
            if (l - lastTimerCall > 1000000000l) {
                setClock();
                lastTimerCall = l;
            }
        }
    };

    @Override public void start(Stage stage) throws Exception {
        hour     = DominoBuilder.create().color(Color.WHITE).animated(true).build();
        minLeft  = DominoBuilder.create().color(Color.WHITE).animated(true).build();
        minRight = DominoBuilder.create().color(Color.WHITE).animated(true).build();
        secLeft  = DominoBuilder.create().color(Color.WHITE).animated(true).build();
        secRight = DominoBuilder.create().color(Color.WHITE).animated(true).build();

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.add(hour, 0, 0);
        GridPane.setMargin(hour, new Insets(0, 25, 0, 0));
        pane.add(minLeft, 1, 0);
        pane.add(minRight, 2, 0);
        GridPane.setMargin(minRight, new Insets(0, 25, 0, 0));
        pane.add(secLeft, 3, 0);
        pane.add(secRight, 4, 0);

        final Scene scene = new Scene(pane, 770, 400, Color.rgb(61, 61, 61));

        stage.setTitle("JavaFX Friday Fun");
        stage.setScene(scene);
        stage.show();

        timer.start();
    }

    private void setClock() {
        Calendar cal = Calendar.getInstance();
        int hh = cal.get(Calendar.HOUR);
        int mm = cal.get(Calendar.MINUTE);
        int ss = cal.get(Calendar.SECOND);

        // Hours
        hour.setNumber(hh);

        // Minutes
        if (mm < 10) {
            minLeft.setNumber(-1);
            minRight.setNumber(mm);
        } else {
            minLeft.setNumber(Integer.parseInt(Integer.toString(mm).substring(0, 1)));
            minRight.setNumber(Integer.parseInt(Integer.toString(mm).substring(1, 2)));
        }

        // Seconds
        if (ss < 10) {
            secLeft.setNumber(-1);
            secRight.setNumber(ss);
        } else {
            secLeft.setNumber(Integer.parseInt(Integer.toString(ss).substring(0, 1)));
            secRight.setNumber(Integer.parseInt(Integer.toString(ss).substring(1, 2)));
        }
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}
