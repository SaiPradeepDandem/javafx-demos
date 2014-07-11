package com.ezest.javafx.sscce;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SlideEffectDemo extends Application {

	private Rectangle2D boxBounds = new Rectangle2D(100, 100, 250, 200);
	
	private StackPane bottomPane;
	private StackPane topPane;
	private Rectangle clipRect;
	private Timeline timelineUp;
	private Timeline timelineDown;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.autosize();
		Scene scene = new Scene(root);
		stage.setTitle("Slide Effect Demo");
		stage.setWidth(350);
	    stage.setHeight(300);
	    stage.setScene(scene);
	    stage.show();
	    
		configureBox(root);
	}

	private void configureBox(VBox root) {
		StackPane container = new StackPane();
		container.setPrefHeight(250);
		container.setPrefSize(boxBounds.getWidth(), boxBounds.getHeight());
		container.setStyle("-fx-border-width:1px;-fx-border-style:solid;-fx-border-color:#999999;");
		
		/* BOTTOM PANE */
		Stop[] stops = new Stop[] { new Stop(0, Color.web("#F89C8C")), new Stop(1, Color.web("#BE250A"))};
		LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		bottomPane = new StackPane();
		bottomPane.getChildren().addAll(RectangleBuilder.create().width(boxBounds.getWidth()).height(boxBounds.getHeight()).fill(lg).build(), 
				 TextBuilder.create().text("Click the above \"Slide Down\" button to see the top pane content...").wrappingWidth(200).font(Font.font("Arial", 22)).build());
		
		/* TOP PANE */
		Stop[] stops2 = new Stop[] { new Stop(0, Color.web("#FFFFFF")), new Stop(1, Color.web("#50AABC"))};
		LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops2);
		StackPane sp1 = new StackPane();
		sp1.getChildren().add(TextBuilder.create().text("Click the below \"Slide Up\" button to see the bottom pane content...").wrappingWidth(200).font(Font.font("Arial", 22)).build());
		
		topPane = new StackPane();
		topPane.getChildren().addAll(RectangleBuilder.create().width(boxBounds.getWidth()).height(boxBounds.getHeight()).fill(lg2).build(), sp1);
		container.getChildren().addAll(bottomPane,topPane);
		
		setAnimation();
		
		Group gp = new Group();
		gp.getChildren().add(container);
		root.getChildren().addAll(getActionPane(),gp);
	}

	private void setAnimation(){
		/* Initially hiding the Top Pane*/
		clipRect = new Rectangle();
        clipRect.setWidth(boxBounds.getWidth());
		clipRect.setHeight(0);
		clipRect.translateYProperty().set(boxBounds.getHeight());
		topPane.setClip(clipRect);
		topPane.translateYProperty().set(-boxBounds.getHeight());
		
		/* Animation for bouncing effect. */
		final Timeline timelineBounce = new Timeline();
		timelineBounce.setCycleCount(2);
		timelineBounce.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (boxBounds.getHeight()-15));
		final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
		final KeyValue kv3 = new KeyValue(topPane.translateYProperty(), -15);
		final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
		timelineBounce.getKeyFrames().add(kf1);
		
		/* Event handler to call bouncing effect after the scroll down is finished. */
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	timelineBounce.play();
            }
        };
        
        timelineDown = new Timeline();
        timelineUp = new Timeline();
        
        /* Animation for scroll down. */
		timelineDown.setCycleCount(1);
		timelineDown.setAutoReverse(true);
		final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), boxBounds.getHeight());
		final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
		final KeyValue kvDwn3 = new KeyValue(topPane.translateYProperty(), 0);
		final KeyFrame kfDwn = new KeyFrame(Duration.millis(200), onFinished, kvDwn1, kvDwn2, kvDwn3);
		timelineDown.getKeyFrames().add(kfDwn);
		
		/* Animation for scroll up. */
		timelineUp.setCycleCount(1); 
		timelineUp.setAutoReverse(true);
		final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
		final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight());
		final KeyValue kvUp3 = new KeyValue(topPane.translateYProperty(), -boxBounds.getHeight());
		final KeyFrame kfUp = new KeyFrame(Duration.millis(200), kvUp1, kvUp2, kvUp3);
		timelineUp.getKeyFrames().add(kfUp);
	}
	
	private HBox getActionPane(){
		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(10);
		hb.setPrefHeight(40);
		Button upBtn = new Button("Slide Up");
		upBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timelineUp.play();
			}
		});
		Button downBtn = new Button("Slide Down");
		downBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timelineDown.play();
			}
		});
		hb.getChildren().addAll(downBtn,upBtn);
		return hb;
	}
}
