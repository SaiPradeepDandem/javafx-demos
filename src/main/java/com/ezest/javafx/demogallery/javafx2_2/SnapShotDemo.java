package com.ezest.javafx.demogallery.javafx2_2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.PerspectiveCameraBuilder;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;

import com.ezest.javafx.uicontrols.Person;
import com.javafx.experiments.scenicview.ScenicView;

/**
 * https://blogs.oracle.com/thejavatutorials/entry/take_a_snaphot_with_javafx
 * @author Sai.Dandem
 *
 */
public class SnapShotDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	StackPane cont = new StackPane();
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		VBox vb = new VBox();
		
		final Button btn = new Button("Simple SnapShot");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showSimpleSnapShot();
			}
		});
		
		Button btn2 = new Button("Parameters SnapShot");
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showParametersSnapShot();
				
			}
		});
		ImageView i = new ImageView(new Image(getClass().getResourceAsStream("/images/cutrope.jpg")));
		i.setFitHeight(500);
		i.setFitWidth(500);
		cont.setAlignment(Pos.TOP_LEFT);
		cont.getChildren().addAll(i, new Button("hildi"));
		
		vb.getChildren().addAll(new TextField(), btn,btn2,cont);
		root.getChildren().add(vb);
	}
	
	private void showSimpleSnapShot(){
		WritableImage image = new WritableImage(400, 400);
		scene.snapshot(image);
		generateImageFile(image);
		openStage(image);
	}
	double sFactor = 1;
	private void showParametersSnapShot(){
		Callback<SnapshotResult,java.lang.Void> callBack = new Callback<SnapshotResult, Void>() {
			@Override
			public Void call(SnapshotResult result) {
				SnapshotParameters param = result.getSnapshotParameters();
				return null;
			}
		};
		SnapshotParameters param = new SnapshotParameters(); 
		param.setCamera(PerspectiveCameraBuilder.create().fieldOfView(45).build());
		param.setDepthBuffer(true);
		param.setFill(Color.BLUE);
		
		
		Shear shear = new Shear(0.7, 0);
		Rotate rt = new Rotate(5);
		Scale scl = new Scale(sFactor, sFactor);
		
		param.setTransform(scl);
		
		int w = (int) (cont.getWidth() * sFactor);
		int h = (int) (cont.getHeight() * sFactor);
		System.out.println("Ws : " + w + " Hs : " + h);
		WritableImage image = new WritableImage(w, h);
		cont.snapshot(callBack,param, image);
		
		generateImageFile(image);
		//openStage(image);
		clipImageAndOpenStage(image);
	}
	
	private WritableImage src;
	private void clipImageAndOpenStage(Image image) {
		
		/*int w = (int) (cont.getWidth() * sFactor);
		int h = (int) (cont.getHeight() * sFactor);
		System.out.println("Ws : " + w + " Hs : " + h);
		
		PixelReader reader = image.getPixelReader();
		WritablePixelFormat<IntBuffer> format = WritablePixelFormat.getIntArgbInstance();
		//int kernalWidth = (int) (radius * 2);
		int[] buffer = new int[(int) (150 * 150)];
		reader.getPixels(0, 0, 150, 150, format, buffer, 0, 150);

		WritableImage dest = new WritableImage(150, 150);
		PixelWriter writer = dest.getPixelWriter();
		int k = 0;
		for (int x = 0; x < 150; x++) {
			for (int y = 0; y < 150; y++) {
				k++;
				System.out.println(reader.getColor(x, y));
				//writer.setColor(x, y, reader.getColor(x, y));
			}
		}*/
		
		//System.out.println("Tota : " + k);
		
		ImageView imageView = new ImageView(image);
		VBox pane = VBoxBuilder.create().spacing(15).build();
		pane.setStyle("-fx-border-color:red;-fx-border-width:1px;");
		ClippedNode clipped = new ClippedNode(imageView, 100, 100);
		pane.getChildren().add(clipped);
		
		Slider s1 = new Slider(0, 500, 100);
		clipped.transXProperty().bind(s1.valueProperty());
		pane.getChildren().add(s1);
		
		Slider s2 = new Slider(0, 500, 100);
		clipped.transYProperty().bind(s2.valueProperty());
		pane.getChildren().add(s2);
		
		StackPane sp = new StackPane();
		sp.setStyle("-fx-border-color:green;-fx-border-width:1px;");
		sp.getChildren().add(pane);
		Stage stg = new Stage();
		stg.setWidth(440);
		stg.setHeight(470);
		stg.setScene(new Scene(sp));
		stg.show();
		//ScenicView.show(sp);
		
		
	}

	private void openStage(Image image){
		StackPane sp = new StackPane();
		sp.setStyle("-fx-border-color:red;-fx-border-width:1px;");
		sp.setMaxSize(100, 100);
		sp.getChildren().add(new ImageView(image));
		
		Stage stg = new Stage();
		stg.setWidth(440);
		stg.setHeight(470);
		stg.setScene(new Scene(StackPaneBuilder.create().children(sp).build()));
		stg.show();
	}
	
	private void generateImageFile(WritableImage image){
		try {
		    BufferedImage bimg = convertToAwtImage(image);
		    File outputfile = new File("saved.png");
		    ImageIO.write(bimg, "png", outputfile);
		} catch (IOException e) {
		   System.out.println("");
		}
	}

	private java.awt.image.BufferedImage convertToAwtImage(javafx.scene.image.Image fxImage) {
		if (Image.impl_isExternalFormatSupported(BufferedImage.class)) {
			java.awt.image.BufferedImage awtImage = new BufferedImage((int)fxImage.getWidth(), (int)fxImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			return (BufferedImage)fxImage.impl_toExternalImage(awtImage);
		} else {
			return null;
		}
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
		scene.getStylesheets().add("styles/template.css");
	}

	private Node getBottom() {
		StackPane sp = new StackPane();
		sp.setMinHeight(25);
		sp.setAlignment(Pos.TOP_RIGHT);
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/mglass.gif")));
		image.setCursor(Cursor.HAND);
		image.setTranslateX(-5);
		image.setTranslateY(3);
		image.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				ScenicView.show(scene);
			}
		});
		sp.getChildren().addAll(new Separator(),image);
		return sp;
	}
	
	class ClippedNode extends Region {
		private final Node content;
		private final double width;
		private final double height;
		private final Rectangle clip;
		private SimpleDoubleProperty transX = new SimpleDoubleProperty();
		private SimpleDoubleProperty transY = new SimpleDoubleProperty();
		public ClippedNode(Node content, double width, double height) {
			this.width = width;
			this.height = height;
			this.content = content;
			this.clip = RectangleBuilder.create().width(width).height(height).build();
			this.content.setClip(this.clip);
			
			this.content.translateXProperty().bind(transX.multiply(-1));
			this.content.translateYProperty().bind(transY.multiply(-1));
			this.clip.translateXProperty().bind(transX);
			this.clip.translateYProperty().bind(transY);
			getChildren().setAll(content);
		}

		@Override
		protected double computeMinWidth(double d) {
			return width;
		}

		@Override
		protected double computeMinHeight(double d) {
			return height;
		}

		@Override
		protected double computePrefWidth(double d) {
			return width;
		}

		@Override
		protected double computePrefHeight(double d) {
			return height;
		}

		@Override
		protected double computeMaxWidth(double d) {
			return width;
		}

		@Override
		protected double computeMaxHeight(double d) {
			return height;
		}
		public SimpleDoubleProperty transXProperty() {
			return transX;
		}
		public SimpleDoubleProperty transYProperty() {
			return transY;
		}
	}// eo ClippedNode

}


