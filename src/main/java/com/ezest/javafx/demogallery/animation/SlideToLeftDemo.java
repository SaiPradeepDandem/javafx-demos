package com.ezest.javafx.demogallery.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.javafx.experiments.scenicview.ScenicView;
import com.sun.javafx.scene.traversal.Direction;

public class SlideToLeftDemo extends Application {

	Stage stage;
	Scene scene;
	BorderPane root;
	StackPane centerContainer;
	double SLIDE_BUTTON_WIDTH = 30;
	double CENTER_LEFT_PADDING = 10;
	Rectangle clip;
	StackPane patientView;
	HBox slidePane;
	private SimpleBooleanProperty showSlide = new SimpleBooleanProperty();
	
	private SimpleStringProperty btnTxt = new SimpleStringProperty("Show Last Contact Letter");
	private SimpleDoubleProperty arrowRotate = new SimpleDoubleProperty(180);
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		this.root = new BorderPane();
		this.root.setPadding(new Insets(20));
		this.root.setTop(StackPaneBuilder.create().style("-fx-border-color:red;-fx-border-width:1px;-fx-background-color:white;").padding(new Insets(0,0,10,0)).prefHeight(100).build());
		this.root.setLeft(StackPaneBuilder.create().style("-fx-border-color:red;-fx-border-width:1px;-fx-background-color:white;").prefWidth(200).build());
		this.root.setBottom(StackPaneBuilder.create().style("-fx-border-color:red;-fx-border-width:1px;-fx-background-color:white;").padding(new Insets(10,0,0,0)).prefHeight(50).build());
		
		centerContainer = StackPaneBuilder.create().style("-fx-background-color:white;").build();
		this.root.setCenter(centerContainer);
		
		this.scene = new Scene(this.root);
		stage.setTitle("Slide To Left Demo");
		stage.setWidth(1024);
	    stage.setHeight(600);
	    stage.setScene(this.scene);
	    stage.show();
	    ScenicView.show(scene);
	    
	    configureSlide();
	}

	private void configureSlide() {
		patientView =  new StackPane();
		patientView.setStyle("-fx-background-color: linear-gradient(to bottom, #4977A3, #B0C6DA, #9CB6CF);");
		
		StackPane spacer1 = StackPaneBuilder.create().minWidth(SLIDE_BUTTON_WIDTH).build();
		StackPane spacer2 = StackPaneBuilder.create().minWidth(SLIDE_BUTTON_WIDTH).build();
		HBox basePane = HBoxBuilder.create().padding(new Insets(0,0,0,10)).build();
		basePane.getChildren().addAll(patientView, spacer1);
		HBox.setHgrow(patientView, Priority.ALWAYS);
		
		  
		slidePane = HBoxBuilder.create().build();
		slidePane.setAlignment(Pos.CENTER_RIGHT);
		
		StackPane slideButton = StackPaneBuilder.create().style(slideButtonCss()).minWidth(SLIDE_BUTTON_WIDTH).build();
		
		HBox hb = new HBox();
		hb.setSpacing(20);
		hb.setAlignment(Pos.CENTER);
		
		Label btnLbl =LabelBuilder.create().style("-fx-font-family:verdana;-fx-font-size:15;-fx-font-weight:bold;-fx-text-fill:#6D6D6D;").build();
		btnLbl.textProperty().bind(btnTxt);
		
		hb.getChildren().addAll(getArrow(),btnLbl,getArrow());
		hb.setRotate(-90);
		slideButton.getChildren().add(GroupBuilder.create().children(hb).build());
		
		slideButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				showSlide.set(!showSlide.get());
			}
		});
		
		StackPane contactPane = StackPaneBuilder.create().style(slideContentCss()).children(new Label("This is check for length of the contact pane demo in slide.")).build();
		
		StackPane contactPaneSmallContainer = new StackPane();
		contactPaneSmallContainer.getChildren().add(contactPane);
		
		slidePane.getChildren().addAll(slideButton, contactPaneSmallContainer, spacer2);
		HBox.setHgrow(contactPaneSmallContainer, Priority.ALWAYS);
		
		clip = new Rectangle();
		clip.setWidth(SLIDE_BUTTON_WIDTH);
		clip.heightProperty().bind(centerContainer.heightProperty());
		
		slidePane.setClip(clip);
		
		// The listener need to be declared after the showSlide is set to default value.
		showSlide.set(false);
		showSlide.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean value) {
				if(value){
					btnTxt.set("Show Medical Record");
					arrowRotate.set(0);
					animateToShow();
				}else{
					btnTxt.set("Show Last Contact Letter");
					arrowRotate.set(180);
					animateToClose();
				}
			}
		});
		centerContainer.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> paramObservableValue,Number paramT1, Number width) {
				if(showSlide.get()){
					clip.setWidth(width.doubleValue()-(SLIDE_BUTTON_WIDTH + CENTER_LEFT_PADDING));
				}else{
					slidePane.setTranslateX(width.doubleValue()- (SLIDE_BUTTON_WIDTH + CENTER_LEFT_PADDING));
				}
			}
		});
		
		centerContainer.getChildren().addAll(basePane,StackPaneBuilder.create().padding(new Insets(0,0,0,10)).children(slidePane).build());
	}
	
	private void animateToShow(){
		double width = centerContainer.getWidth()-(SLIDE_BUTTON_WIDTH + CENTER_LEFT_PADDING);
		
		final Timeline timelineOpenB = new Timeline();
		timelineOpenB.setCycleCount(2); 
		timelineOpenB.setAutoReverse(true);
		final KeyValue kvb1 = new KeyValue(clip.widthProperty(), width-15);
		final KeyValue kvb2 = new KeyValue(slidePane.translateXProperty(), 15);
		final KeyFrame kfb1 = new KeyFrame(Duration.valueOf("100ms"), kvb1, kvb2);
		timelineOpenB.getKeyFrames().add(kfb1);
		
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	timelineOpenB.play();
            }
        };
        
		final Timeline timelineOpen = new Timeline();
		timelineOpen.setCycleCount(1); 
		timelineOpen.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clip.widthProperty(), width);
		final KeyValue kv2 = new KeyValue(slidePane.translateXProperty(), 0);
		final KeyFrame kf1 = new KeyFrame(Duration.valueOf("200ms"), onFinished, kv1, kv2);
		timelineOpen.getKeyFrames().add(kf1);
		
		timelineOpen.play();
	}
	
	private void animateToClose(){
		double width = centerContainer.getWidth()-(SLIDE_BUTTON_WIDTH + CENTER_LEFT_PADDING);
		
		final Timeline timelineCloseB = new Timeline();
		timelineCloseB.setCycleCount(2); 
		timelineCloseB.setAutoReverse(true);
		final KeyValue kvb1 = new KeyValue(clip.widthProperty(), SLIDE_BUTTON_WIDTH+15);
		final KeyValue kvb2 = new KeyValue(slidePane.translateXProperty(), width-15);
		final KeyFrame kfb1 = new KeyFrame(Duration.valueOf("100ms"), kvb1, kvb2);
		timelineCloseB.getKeyFrames().add(kfb1);
		
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	timelineCloseB.play();
            }
        };
        
		final Timeline timelineClose = new Timeline();
		timelineClose.setCycleCount(1); 
		timelineClose.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clip.widthProperty(), SLIDE_BUTTON_WIDTH);
		final KeyValue kv2 = new KeyValue(slidePane.translateXProperty(), width);
		final KeyFrame kf1 = new KeyFrame(Duration.valueOf("200ms"), onFinished, kv1, kv2);
		timelineClose.getKeyFrames().add(kf1);
		
		timelineClose.play();
	}
	
	private String slideButtonCss(){
		StringBuilder sb = new StringBuilder();
		sb.append("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
		sb.append("-fx-background-insets: 0 0 -1 0, 0, 1, 2;");
		sb.append("-fx-background-radius: 5, 5, 4, 3;");
		sb.append("-fx-padding: 0.166667em 0.4166665em 0.25em 0.4166665em;");
		sb.append("-fx-cursor:hand;");
		return sb.toString();
	}
	private String slideContentCss(){
		StringBuilder sb = new StringBuilder();
		sb.append("-fx-background-color: #585858,#FFFFFF;-fx-background-insets:0, 60 0 0 0;-fx-border-width:1px;-fx-border-color:#444444;");
		return sb.toString();
	}
	
	public Path getArrow(){
		Path arrow = PathBuilder.create()
						  .elements(new MoveTo(0, 0),
						  			new LineTo(6, 6),
						  			new LineTo(12, 0),
						  			new MoveTo(0, 6),
						  			new LineTo(6, 12),
						  			new LineTo(12, 6))
						  .stroke(Color.web("#5D5D5D"))
						  .strokeWidth(2)
						  .build();
		arrow.rotateProperty().bind(arrowRotate);
		return arrow;
	}
}
