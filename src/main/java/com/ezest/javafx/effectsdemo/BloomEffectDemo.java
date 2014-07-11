package com.ezest.javafx.effectsdemo;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BloomEffectDemo {

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
        t.setFont(Font.font("null", FontWeight.BOLD, 36));
        t.setX(25);
        t.setY(65);
 
        g.setCache(true);
        //g.setEffect(new Bloom());
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.3);
        g.setEffect(bloom);
        g.getChildren().add(r);
        g.getChildren().add(t);
        g.setTranslateX(350);
        return g;
    }
}
