package com.ezest.javafx.effectsdemo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TextEffects extends Application {

    Stage stage;
    Scene scene;

    @Override public void start(Stage stage) {
        stage.show();

        scene = new Scene(new Group(), 800, 750);
        ObservableList content = ((Group)scene.getRoot()).getChildren();

        /// Perspective
        content.add(perspective());
        /// DropShadow
        content.add(dropShadow());
        /// Bloom
        content.add(bloom());
        /// BoxBlur
        content.add(boxBlur());
        /// DisplacementMap
        content.add(displacementMap());
        /// InnerShadow
        content.add(innerShadow());
        /// Lighting
        content.add(lighting());
        /// MotionBlur
        content.add(motionBlur());
        /// Reflection
        content.add(reflection());
        /// GaussianBlur
        content.add(gaussianBlur());
        /// DistantLight
        content.add(distantLight());
        stage.setScene(scene);
        stage.setTitle("Text Effects");
    }

    static Node distantLight() {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0f);
        light.setElevation(30.0f);

        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(5.0f);

        final Text t = new Text();
        t.setText("DistantLight");
        t.setFill(Color.RED);
        t.setFont(Font.font(null, FontWeight.BOLD, 70));
        t.setX(10.0f);
        t.setY(10.0f);
        t.setTextOrigin(VPos.TOP);

        t.setEffect(l);

        final Rectangle r = new Rectangle();
        r.setFill(Color.BLACK);

        Group g = new Group();
        g.getChildren().add(r);
        g.getChildren().add(t);

        g.setTranslateY(470);

        return g;
    }

    static Node perspective() {
        Group g = new Group();
        PerspectiveTransform pt = new PerspectiveTransform();
        pt.setUlx(10.0f);
        pt.setUly(10.0f);
        pt.setUrx(310.0f);
        pt.setUry(40.0f);
        pt.setLrx(310.0f);
        pt.setLry(60.0f);
        pt.setLlx(10.0f);
        pt.setLly(90.0f);

        g.setEffect(pt);
        g.setCache(true);

        Rectangle r = new Rectangle();
        r.setX(10.0f);
        r.setY(10.0f);
        r.setWidth(280.0f);
        r.setHeight(80.0f);
        r.setFill(Color.BLUE);

        Text t = new Text();
        t.setX(20.0f);
        t.setY(65.0f);
        t.setText("Perspective");
        t.setFill(Color.YELLOW);
        t.setFont(Font.font(null, FontWeight.BOLD, 36));

        g.getChildren().add(r);
        g.getChildren().add(t);
        return g;
    }

    static Node gaussianBlur() {
        Text t2 = new Text();
        t2.setX(10.0f);
        t2.setY(140.0f);
        t2.setCache(true);
        t2.setText("Blurry Text");
        t2.setFill(Color.RED);
        t2.setFont(Font.font(null, FontWeight.BOLD, 36));
        t2.setEffect(new GaussianBlur());
        return t2;
    }

    static Node reflection() {
        Text t = new Text();
        t.setX(10.0f);
        t.setY(50.0f);
        t.setCache(true);
        t.setText("Reflections on JavaFX...");
        t.setFill(Color.RED);
        t.setFont(Font.font(null, FontWeight.BOLD, 30));

        Reflection r = new Reflection();
        r.setFraction(0.7f);

        t.setEffect(r);

        t.setTranslateY(400);
        return t;
    }

    static Node motionBlur() {
        Text t = new Text();
        t.setX(100.0f);
        t.setY(100.0f);
        t.setText("Motion");
        t.setFill(Color.RED);
        t.setFont(Font.font(null, FontWeight.BOLD, 60));

        MotionBlur mb = new MotionBlur();
        mb.setRadius(15.0f);
        mb.setAngle(-30.0f);

        t.setEffect(mb);

        t.setTranslateX(300);
        t.setTranslateY(150);

        return t;
    }

    static Node lighting() {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0f);

        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(5.0f);

        Text t = new Text();
        t.setText("Lighting!");
        t.setFill(Color.RED);
        t.setFont(Font.font(null, FontWeight.BOLD, 70));
        t.setX(10.0f);
        t.setY(10.0f);
        t.setTextOrigin(VPos.TOP);

        t.setEffect(l);

        t.setTranslateX(0);
        t.setTranslateY(320);

        return t;
    }

    static Node innerShadow() {
        InnerShadow is = new InnerShadow();
        is.setOffsetX(4.0f);
        is.setOffsetY(4.0f);

        Text t = new Text();
        t.setEffect(is);
        t.setX(20);
        t.setY(100);
        t.setText("InnerShadow");
        t.setFill(Color.YELLOW);
        t.setFont(Font.font(null, FontWeight.BOLD, 80));

        t.setTranslateX(350);
        t.setTranslateY(300);

        return t;
    }

    static Node displacementMap() {
        int w = 220;
        int h = 100;
        FloatMap map = new FloatMap();
        map.setWidth(w);
        map.setHeight(h);

        for (int i = 0; i < w; i++) {
            double v = (Math.sin(i / 50.0 * Math.PI) - 0.5) / 40.0;
            for (int j = 0; j < h; j++) {
                map.setSamples(i, j, 0.0f, (float) v);
            }
        }

        Group g = new Group();
        DisplacementMap dm = new DisplacementMap();
        dm.setMapData(map);

        Rectangle r = new Rectangle();
        r.setX(80.0f);
        r.setY(40.0f);
        r.setWidth(w);
        r.setHeight(h);
        r.setFill(Color.BLUE);

        g.getChildren().add(r);

        Text t = new Text();
        t.setX(100.0f);
        t.setY(80.0f);
        t.setText("Wavy Text");
        t.setFill(Color.YELLOW);
        t.setFont(Font.font(null, FontWeight.BOLD, 36));

        g.getChildren().add(t);

        g.setEffect(dm);
        g.setCache(true);

        g.setTranslateX(300);
        g.setTranslateY(180);

        return g;
    }

    static Node boxBlur() {
        Text t = new Text();
        t.setText("Blurry Text!");
        t.setFill(Color.RED);
        t.setFont(Font.font(null, FontWeight.BOLD, 36));
        t.setX(10);
        t.setY(40);

        BoxBlur bb = new BoxBlur();
        bb.setWidth(15);
        bb.setHeight(15);
        bb.setIterations(3);

        t.setEffect(bb);
        t.setTranslateX(350);
        t.setTranslateY(100);

        return t;
    }


    static Node dropShadow() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        
        Text t = new Text();
        t.setEffect(ds);
        t.setCache(true);
        t.setX(10.0f);
        t.setY(270.0f);
        t.setFill(Color.RED);
        t.setText("JavaFX drop shadow...");
        t.setFont(Font.font(null, FontWeight.BOLD, 32));

        return t;
    }

    static Node bloom() {
        Group g = new Group();

        Rectangle r = new Rectangle();
        r.setX(10);
        r.setY(10);
        r.setWidth(160);
        r.setHeight(80);
        r.setFill(Color.DARKBLUE);

        Text t = new Text();
        t.setText("Bloom!");
        t.setFill(Color.YELLOW);
        t.setFont(Font.font(null, FontWeight.BOLD, 36));
        t.setX(25);
        t.setY(65);

        g.setCache(true);
        g.setEffect(new Bloom());
        g.getChildren().add(r);
        g.getChildren().add(t);
        g.setTranslateX(350);
        return g;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch (args);
    }
}
