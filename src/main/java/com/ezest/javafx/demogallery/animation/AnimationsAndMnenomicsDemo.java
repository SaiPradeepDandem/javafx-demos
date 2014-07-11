package com.ezest.javafx.demogallery.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.javafx.experiments.scenicview.ScenicView;

public class AnimationsAndMnenomicsDemo extends Application {

	Stage stage;
	Scene scene;
	ScrollPane mainRoot;
	FlowPane root;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		MenuBar mb = new MenuBar();
		
		Menu m = new Menu("Hello");
		MenuItem m1 = new MenuItem("First Item");
		m1.getStyleClass().add("myMenuItem");
		//m1.setMnemonicParsing(true);
		KeyCombination kc = new KeyCharacterCombination("A", KeyCombination.SHIFT_DOWN);
		m1.setAccelerator( kc );  
		m1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("First Item Printed");
				
			}
		});
		MenuItem m2 = new MenuItem("Sec_ond Item");
		m2.setMnemonicParsing(true);
		m2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Second Printed");
				
			}
		});
		m.getItems().addAll(m1,m2);
		mb.getMenus().add(m);
		root.getChildren().addAll(mb);
		
		moveBoxLayout();
		clipLayout3();
		clipLayout2();
		clipLayout();
		configureSlideToLeft();
		
	}

	
	private void configureStage(){
		stage.setTitle("Animations Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	    ScenicView.show(scene);
	}
	
	private void configureScene(){
		mainRoot = new ScrollPane();
		root = new FlowPane();
		root.minWidthProperty().bind(mainRoot.widthProperty());
		root.setHgap(10);
		root.setVgap(10);
		root.autosize();
		mainRoot.setContent(root);
		this.scene = new Scene(mainRoot, Color.LINEN);
		scene.getStylesheets().add("styles/sample.css");
		
	}
	
	private void moveBoxLayout(){
		final Rectangle rectBasicTimeline = new Rectangle(100, 50, 100, 50);
		rectBasicTimeline.setFill(Color.BLUE);
		
		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1); //Timeline.INDEFINITE
		timeline.setAutoReverse(true);
		final KeyValue kv = new KeyValue(rectBasicTimeline.translateXProperty(),600);
		final KeyFrame kf = new KeyFrame(Duration.valueOf("3000ms"), kv);
		timeline.getKeyFrames().add(kf);
		
		
		Button btn = new Button("Move _Forward");
		btn.setMnemonicParsing(true);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timeline.play();
			}
		});
		
		final Timeline timeline2 = new Timeline();
		timeline2.setCycleCount(1); //Timeline.INDEFINITE
		timeline2.setAutoReverse(true);
		final KeyValue kv2 = new KeyValue(rectBasicTimeline.translateXProperty(), 0);
		final KeyFrame kf2 = new KeyFrame(Duration.valueOf("500ms"), kv2);
		timeline2.getKeyFrames().add(kf2);
		
		
		Button btn2 = new Button("Move _Backward");
		btn2.setMnemonicParsing(true);
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timeline2.play();
			}
		});
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.getChildren().addAll(btn,btn2);
		
		
		
		final Button btn3 = new Button("Change me");
		final Timeline timeline3 = new Timeline();
		timeline3.setCycleCount(1); 
		timeline.setAutoReverse(true);
		final KeyValue kv3 = new KeyValue(btn3.styleProperty(),"-fx-background-color:blue;");
		final KeyFrame kf3 = new KeyFrame(Duration.valueOf("3000ms"), kv3);
		timeline3.getKeyFrames().add(kf3);
		
		btn3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				btn3.setStyle("-fx-background-color:red");
				timeline3.play();
				timeline.play();
			}
		});
		hb.getChildren().addAll(btn3);
		
	/* AUTO REPEATING THREAD START*/
		
		final Button btn4 = new Button("Repeat Me");
		final Timeline repeatTimeLine = new Timeline(
			    new KeyFrame(Duration.seconds(30), new EventHandler<ActionEvent>() {
			      @Override
			      public void handle(ActionEvent event) {
			         System.out.println("I am repeating after 30 secs.......");
			         if(btn4.getStyle().equals("-fx-background-color:red")){
			        	 btn4.setStyle("-fx-background-color:blue");
			         }else{
			        	 btn4.setStyle("-fx-background-color:red"); 
			         }
			      }
			    })
			  );
			
		btn4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				repeatTimeLine.setCycleCount(Timeline.INDEFINITE);
				repeatTimeLine.play();
			}
		});
		hb.getChildren().addAll(btn4);
		
	/* AUTO REPEATING THREAD END */
		
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.getChildren().addAll(hb,rectBasicTimeline);
		
		root.getChildren().addAll(vb);
	}

	private void configureSlideToLeft() {
		StackPane container = new StackPane();
		container.setMinSize(400, 250);
		container.setStyle("-fx-border-color:red;-fx-border-width:1px;");
		
		StackPane basePane =  new StackPane();
		basePane.setStyle("-fx-background-color: linear-gradient(to bottom, #4977A3, #B0C6DA, #9CB6CF);");
		
		HBox slidePane = HBoxBuilder.create().padding(new Insets(0,0,0,10)).build();
		StackPane slideButton = StackPaneBuilder.create().style(slideButtonCss()).minWidth(30).build();
		StackPane slideContent = StackPaneBuilder.create().style(slideContentCss()).children(new Label("This is check for length of the contact pane demo in slide.")).build();
		slidePane.getChildren().addAll(slideButton,slideContent);
		HBox.setHgrow(slideContent, Priority.ALWAYS);
		
		Rectangle clipRect = new Rectangle();
		
		container.getChildren().addAll(slidePane);
		root.getChildren().addAll(container);
	}

	
	private void clipLayout(){
		VBox vb = new VBox();
		vb.setSpacing(10);
		
		StackPane sp = new StackPane();
		sp.prefWidth(300);
		sp.prefHeight(300);
		sp.setAlignment(Pos.TOP_LEFT);
		
		Rectangle rect1 =  new Rectangle(300,300);
		rect1.setFill(Color.BROWN);
		sp.getChildren().add(rect1);
		
		Rectangle clipRect = new Rectangle();
		clipRect.setWidth(300);
		clipRect.setHeight(0);
		
		Rectangle rect2 =  new Rectangle(300,300);
		rect2.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
			    new Stop[]{
			    new Stop(0,Color.web("#4977A3")),
			    new Stop(0.5, Color.web("#B0C6DA")),
			    new Stop(1,Color.web("#9CB6CF")),}));
		rect2.setClip(clipRect);
		
		sp.getChildren().add(rect2);
		
		
		final Timeline timelineDown1 = new Timeline();
		timelineDown1.setCycleCount(2); //Timeline.INDEFINITE
		timelineDown1.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), 285);
		final KeyFrame kf1 = new KeyFrame(Duration.valueOf("100ms"), kv1);
		timelineDown1.getKeyFrames().add(kf1);
		
		EventHandler<ActionEvent> onFinished1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	timelineDown1.play();
            }
        };
        
		final Timeline timelineDown = new Timeline();
		timelineDown.setCycleCount(1); //Timeline.INDEFINITE
		timelineDown.setAutoReverse(true);
		final KeyValue kv = new KeyValue(clipRect.heightProperty(), 300);
		final KeyFrame kf = new KeyFrame(Duration.valueOf("200ms"), onFinished1, kv);
		timelineDown.getKeyFrames().add(kf);
		
		
		Button btn = new Button("Shutter _Down");
		btn.setMnemonicParsing(true);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timelineDown.play();
			}
		});
		
	
		final Timeline timelineUp = new Timeline();
		timelineUp.setCycleCount(1); //Timeline.INDEFINITE
		timelineUp.setAutoReverse(true);
		final KeyValue kvUp = new KeyValue(clipRect.heightProperty(), 0);
		final KeyFrame kfUp = new KeyFrame(Duration.valueOf("200ms"), kvUp);
		timelineUp.getKeyFrames().add(kfUp);
		
		Button btn2 = new Button("Shutter _Up");
		btn.setMnemonicParsing(true);
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timelineUp.play();
			}
		});
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.getChildren().addAll(btn,btn2);
		
		vb.getChildren().addAll(hb,sp);
		root.getChildren().addAll(vb);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void clipLayout3(){
		/* ******************************************************************/		
		Rectangle box =  new Rectangle(300,300);
		box.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
			    new Stop[]{
				new Stop(0,Color.web("#C0C0C0")),
				new Stop(0.25,Color.web("#FF0000")),
			    new Stop(0.5, Color.web("#00FF00")),
			    new Stop(0.75,Color.web("#0000FF")),
			    new Stop(1,Color.web("#C0C0C0")),}));
		
		final Timeline localTimeline = new Timeline();
	      KeyValue localKeyValue = new KeyValue(box.heightProperty(), 0, Interpolator.EASE_OUT);
	      localTimeline.getKeyFrames().clear();
	      localTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(300.0D), new EventHandler()
	      {
	        @Override
			public void handle(Event paramT) {
				 System.out.println("i am done");
				
			}
	      }
	      , new KeyValue[] { localKeyValue }));
	     
	      final Timeline localTimeline2 = new Timeline();
	      KeyValue localKeyValue2 = new KeyValue(box.heightProperty(), 300, Interpolator.EASE_IN);
	      localTimeline2.getKeyFrames().clear();
	      localTimeline2.getKeyFrames().add(new KeyFrame(Duration.millis(300.0D), new EventHandler()
	      {
	        @Override
			public void handle(Event paramT) {
				 System.out.println("i am done");
				
			}
	      }
	      , new KeyValue[] { localKeyValue2 }));
	    
	      final Timeline localTimeline3 = new Timeline();
	      KeyValue localKeyValue3 = new KeyValue(box.widthProperty(), 0, Interpolator.EASE_OUT);
	      localTimeline3.getKeyFrames().clear();
	      localTimeline3.getKeyFrames().add(new KeyFrame(Duration.millis(300.0D), new EventHandler()
	      {
	        @Override
			public void handle(Event paramT) {
				 System.out.println("i am done");
				
			}
	      }
	      , new KeyValue[] { localKeyValue3 }));
	     
	      final Timeline localTimeline4 = new Timeline();
	      KeyValue localKeyValue4 = new KeyValue(box.widthProperty(), 300, Interpolator.EASE_IN);
	      localTimeline4.getKeyFrames().clear();
	      localTimeline4.getKeyFrames().add(new KeyFrame(Duration.millis(300.0D), new EventHandler()
	      {
	        @Override
			public void handle(Event paramT) {
				 System.out.println("i am done");
				
			}
	      }
	      , new KeyValue[] { localKeyValue4 }));
	    
	      
	      Button btn = new Button("EASE UP");
			btn.setMnemonicParsing(true);
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					localTimeline.play();
				}
			});
			Button btn1 = new Button("EASE DWN");
			btn1.setMnemonicParsing(true);
			btn1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					localTimeline2.play();
				}
			});
			
			Button btn2 = new Button("EASE Left");
			btn2.setMnemonicParsing(true);
			btn2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					localTimeline3.play();
				}
			});
			Button btn3 = new Button("EASE Right");
			btn3.setMnemonicParsing(true);
			btn3.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					localTimeline4.play();
				}
			});
			
			HBox hb = new HBox();
			hb.setSpacing(10);
			hb.getChildren().addAll(btn,btn1,btn2,btn3);
			
			VBox vb = new VBox();
			vb.getChildren().addAll(hb,box);
			root.getChildren().addAll(vb);
/* ******************************************************************/		
	}
	private void clipLayout2(){
		
		VBox vb = new VBox();
		vb.setSpacing(10);
		double height =300;
		double width = 300;
		
		StackPane sp = new StackPane();
		sp.prefWidth(width);
		sp.prefHeight(height);
		sp.setAlignment(Pos.TOP_LEFT);
		
		Rectangle rect1 =  new Rectangle(width,height);
		rect1.setFill(Color.GRAY);
		sp.getChildren().add(rect1);
		
		
		Rectangle rect2 =  new Rectangle(width,height);
		rect2.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
			    new Stop[]{
				new Stop(0,Color.web("#C0C0C0")),
				new Stop(0.25,Color.web("#FF0000")),
			    new Stop(0.5, Color.web("#00FF00")),
			    new Stop(0.75,Color.web("#0000FF")),
			    new Stop(1,Color.web("#C0C0C0")),}));
		
		Rectangle clipRect = new Rectangle();
		clipRect.setWidth(width);
		rect2.setClip(clipRect);
		
		/* Initial Positions */
		clipRect.setHeight(0);
		clipRect.setTranslateY(height);
		rect2.setTranslateY(-height);
		
		sp.getChildren().add(rect2);
		
		final Timeline timelineDown1 = new Timeline();
		timelineDown1.setCycleCount(2); 
		timelineDown1.setAutoReverse(true);
		final KeyValue kvd1 = new KeyValue(clipRect.heightProperty(), height-15);
		final KeyValue kvd2 = new KeyValue(clipRect.translateYProperty(), 15);
		final KeyValue kvd3 = new KeyValue(rect2.translateYProperty(), -15);
		final KeyFrame kfd1 = new KeyFrame(Duration.valueOf("100ms"), kvd1, kvd2, kvd3);
		timelineDown1.getKeyFrames().add(kfd1);
		
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	timelineDown1.play();
            }
        };
        
		final Timeline timelineDown = new Timeline();
		timelineDown.setCycleCount(1); 
		timelineDown.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), height);
		final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 0);
		final KeyValue kv3 = new KeyValue(rect2.translateYProperty(), 0);
		final KeyFrame kf1 = new KeyFrame(Duration.valueOf("200ms"), onFinished, kv1, kv2, kv3);
		timelineDown.getKeyFrames().add(kf1);
		
		final Timeline timelineUp = new Timeline();
		timelineUp.setCycleCount(1); 
		timelineUp.setAutoReverse(true);
		final KeyValue kv4 = new KeyValue(clipRect.heightProperty(), 0);
		final KeyValue kv5 = new KeyValue(clipRect.translateYProperty(), height);
		final KeyValue kv6 = new KeyValue(rect2.translateYProperty(), -height);
		final KeyFrame kf2 = new KeyFrame(Duration.valueOf("200ms"), kv4, kv5, kv6);
		timelineUp.getKeyFrames().add(kf2);

		
		Button btn = new Button("Shutter Do_wn");
		btn.setMnemonicParsing(true);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timelineDown.play();
			}
		});
		Button btn1 = new Button("Shutter U_p");
		btn1.setMnemonicParsing(true);
		btn1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timelineUp.play();
			}
		});
		
		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.getChildren().addAll(btn,btn1);
		
		vb.getChildren().addAll(hb,sp);
		root.getChildren().addAll(vb);
		
	}
	
	private String slideButtonCss(){
		StringBuilder sb = new StringBuilder();
		sb.append("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;");
		sb.append("-fx-background-insets: 0 0 -1 0, 0, 1, 2;");
		sb.append("-fx-background-radius: 5, 5, 4, 3;");
		sb.append("-fx-padding: 0.166667em 0.833333em 0.25em 0.833333em;");
		sb.append("-fx-cursor:hand;");
		return sb.toString();
	}
	private String slideContentCss(){
		StringBuilder sb = new StringBuilder();
		sb.append("-fx-background-color: #585858,#FFFFFF;-fx-background-insets:0, 60 0 0 0;");
		return sb.toString();
	}
	
}


