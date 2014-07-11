package com.ezest.javafx.views;

import com.javafx.experiments.scenicview.ScenicView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ScenicViewExample extends Application
{
	protected Group createGroup()
	{
     Group g = new Group();
     Circle c1 = new Circle(35.0D, 25.0D, 20.0D, Color.RED);
     c1.setOpacity(0.7D);
     Circle c2 = new Circle(0.0D, 25.0D, 32.0D, Color.BLUE);
     c2.setOpacity(0.6D);
     Circle c3 = new Circle(55.0D, 25.0D, 12.0D, Color.GREEN);
     c3.setOpacity(0.5D);
     g.getChildren().addAll(new Node[] { c1, c2, c3 });
     return g;
   }
 
   public void start(Stage stage) {
     stage.setTitle("ScenicView Test App");
     TilePane tilepane = new TilePane(12.0D, 12.0D);
     tilepane.setPadding(new Insets(18.0D, 18.0D, 18.0D, 18.0D));
     tilepane.setPrefColumns(4);
 
     DropShadow shadow = new DropShadow();
     shadow.setRadius(30.0D);
 
     Rectangle rect1 = new Rectangle(75.0D, 75.0D, Color.BLUEVIOLET);
     Rectangle rect2 = new Rectangle(75.0D, 75.0D, Color.CRIMSON);
     rect2.setEffect(shadow);
     Rectangle rect3 = new Rectangle(75.0D, 75.0D, Color.AQUAMARINE);
     rect3.setRotate(45.0D);
     Rectangle rect4 = new Rectangle(75.0D, 75.0D, Color.ORANGE);
     rect4.setRotate(45.0D);
 
     Group g1 = createGroup();
     Group g2 = createGroup();
     g2.setEffect(shadow);
     Group g3 = createGroup();
     g3.setRotate(45.0D);
     Group g4 = createGroup();
     g4.setRotate(45.0D);
 
     Font f = new Font(18.0D);
     Button b1 = new Button("First");
     b1.setFont(f);
     Button b2 = new Button("Second");
     b2.setFont(f);
     b2.setEffect(shadow);
     Button b3 = new Button("Third");
     b3.setFont(f);
     b3.setRotate(45.0D);
     Button b4 = new Button("Fourth");
     b4.setRotate(45.0D);
     b4.setFont(f);

    tilepane.getChildren().addAll(new Node[] { rect1, rect2, rect3, new Group(new Node[] { rect4 }), b1, b2, b3, new Group(new Node[] { b4 }), g1, g2, g3, new Group(new Node[] { g4 }) });
 
     Scene scene = new Scene(tilepane);
     stage.setScene(scene);
     stage.show();
 
     ScenicView.show(scene);
   }
/*    */ 
   public static void main(String[] args) {
     Application.launch(args);
   }
}
