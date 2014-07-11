/**
 * 
 */
package com.ezest.javafx.effectsdemo;

import java.net.URL;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.effect.DisplacementMap;
import javafx.scene.effect.DisplacementMapBuilder;
import javafx.scene.effect.FloatMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

/**
 * @author Sai.Dandem
 *
 */
public class DisplacementMapDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	DisplacementMap displacementMap = new DisplacementMap();
	 
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		int width = 220;
		int height = 100;
		 
		 FloatMap floatMap = new FloatMap();
		 floatMap.setWidth(width);
		 floatMap.setHeight(height);

		 for (int i = 0; i < width; i++) {
		     double v = (Math.sin(i / 20.0 * Math.PI) - 0.5) / 40.0;
		     for (int j = 0; j < height; j++) {
		         floatMap.setSamples(i, j, 0, (float) v);
		     }
		 }

		 displacementMap.setMapData(floatMap);

		 Text text = new Text();
		 text.setText("Wavy Text");
		 text.setFill(Color.web("0x3b596d"));
		 text.setFont(Font.font(null, FontWeight.BOLD, 50));
		 text.setEffect(displacementMap);
		 
		 final Slider wSlider = new Slider(200, 1000, 220);
		 final Slider hSlider = new Slider(1, 50, 5);
		 wSlider.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable arg0) {
				compute(wSlider.getValue(), hSlider.getValue());
			}
		});
		 hSlider.valueProperty().addListener(new InvalidationListener() {
				
				@Override
				public void invalidated(Observable arg0) {
					compute(wSlider.getValue(), hSlider.getValue());
				}
			});
		 VBox v = VBoxBuilder.create().spacing(15).padding(new Insets(0,0,0,250)).children(wSlider, hSlider, text).build();
		 
		 final ImageView r = getImage();
		 FloatMap fm = new FloatMap();
		 fm.setWidth((int)r.getLayoutBounds().getWidth());
		 fm.setHeight((int)r.getLayoutBounds().getHeight());
		 for (int i = 0; i < fm.getWidth(); i++) {
			for (int j = 0; j < fm.getHeight(); j++) {
				int value = (int)Math.sin(j/30.0 * Math.PI)/10;
				 double v1 = (Math.sin(i / 50.0 * Math.PI) - 0.5) / 40.0;
				System.out.println(value);
				fm.setSample(i, j, 0,(float) v1);
				//fm.setSample(i, j, 1, -0.50f);
			}
		}
		r.setEffect(DisplacementMapBuilder.create().mapData(fm).wrap(false).build());
		v.getChildren().add(r);
		
		final ImageView r1 = getImage();
		r1.setEffect(DisplacementMapBuilder.create().offsetX(.1).scaleX(.5).scaleY(.5).offsetY(0.1).mapData(fm).wrap(false).build());
		v.getChildren().add(r1);
		
		root.getChildren().add(v);
	}
	
	private ImageView getImage(){
		return new ImageView( new Image(getResource("/images/Koala.jpg")) );
	}
	public static String getResource(String file) {
		URL resource =  DisplacementMapDemo.class.getResource(file);
		return resource == null ? file : resource.toExternalForm();
	}
	private void compute(double w, double h){
		FloatMap floatMap = new FloatMap();
		int w1 = (int)w;
		int h1 =  (int)h;
		 floatMap.setWidth(w1);
		 floatMap.setHeight(h1);

		 for (int i = 0; i < w1; i++) {
		     double v = (Math.sin(i / 20.0 * Math.PI) - 0.5) / 40.0;
		     for (int j = 0; j < h1; j++) {
		         floatMap.setSamples(i, j, 0.0f, (float) v);
		     }
		 }

		 displacementMap.setMapData(floatMap);
	}
	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setBottom(getBottom());
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
		scene.getStylesheets().add("styles/demoTemplate.css");
	}

	private Node getBottom() {
		StackPane sp = new StackPane();
		sp.setMinHeight(25);
		sp.setAlignment(Pos.TOP_RIGHT);
		Circle c = CircleBuilder.create().fill(Color.RED).translateX(-5).translateY(3).radius(8).cursor(Cursor.HAND).build();
		c.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				ScenicView.show(scene);
			}
		});
		sp.getChildren().addAll(new Separator(), c);
		return sp;
	}

}

