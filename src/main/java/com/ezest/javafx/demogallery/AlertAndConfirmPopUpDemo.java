package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.ezest.javafx.components.AbstractPopUpView;

public class AlertAndConfirmPopUpDemo extends Application {

	Stage stage;
	Scene scene;
	Group root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configurePopUps();
	}

	private void configureStage(){
		stage.setTitle("Pop Ups");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new Group();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

	
	private void configurePopUps() {
		VBox vb = new VBox();
		vb.setPadding(new Insets(15));
		
		/** ALERT */
		Label alertLbl = new Label("Alert Demo");
		alertLbl.setTooltip(new Tooltip("Haeildid"));
		alertLbl.setStyle("-fx-font-weight:bold;-fx-font-size:14px;");
		
		final TextField text = new TextField("This is some default text for alert.");
		text.setPrefWidth(200);
		
		Button alertBtn = new Button("Show as Alert");
		alertBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				new Alert(stage, "Alert", text.getText());
			}
			
		});
		
		HBox hb1 = new HBox();
		hb1.setSpacing(10);
		hb1.getChildren().addAll(new Label("Enter Some Text : "),text,alertBtn);
		vb.getChildren().addAll(alertLbl,hb1);
		
		/** CONFIRM */
		Label confirmLbl = new Label("Confirm Demo");
		confirmLbl.setStyle("-fx-font-weight:bold;-fx-font-size:14px;");
		
		final TextField textc = new TextField("This is some default text for confirm. Are you sure ?");
		textc.setPrefWidth(200);
		
		final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                System.out.println("Yes i am done.... !!!");
            }
        };
        Button confirmBtn = new Button("Show as Confirm");
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				new Confirm(stage, "Confirm", textc.getText(), event);
			}
			
		});
		HBox hb2 = new HBox();
		hb2.setSpacing(10);
		hb2.getChildren().addAll(new Label("Enter Some Text : "),textc,confirmBtn);
		vb.getChildren().addAll(confirmLbl,hb2);
		
		
		root.getChildren().addAll(vb);
	}

	class Alert extends MessagePopUp{
		public Alert(Stage parentStage, String title, String message){
			super(parentStage, title, message);
		}
		
		public Alert(Stage parentStage, String title, String message, double width){
			super(parentStage, title, message, width);
		}
	}
	
	class Confirm extends MessagePopUp{

		public Confirm(Stage parentStage, String title, String message, EventHandler<ActionEvent> event) {
			super(parentStage, title, message);
			setActionBox(event);
		}
		
		public Confirm(Stage parentStage, String title, String message, double width, EventHandler<ActionEvent> event){
			super(parentStage, title, message, width);
			setActionBox(event);
		}
	
		private void setActionBox(final EventHandler<ActionEvent> event) {
			final HBox actionBox = super.getActionBox();
			actionBox.getChildren().clear();
			actionBox.setSpacing(15);
			
			Button okBtn = new Button("Ok");
		    okBtn.setPrefWidth(75);
		    okBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent actionEvent) {
	            	event.handle(actionEvent);
	                getStage().close();
	            }
	        });
		    
		    Button cancelBtn = new Button("Cancel");
		    cancelBtn.setPrefWidth(75);
		    cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent actionEvent) {
	                getStage().close();
	            }
	        });
		    actionBox.getChildren().addAll(okBtn,cancelBtn);
		}
	}

	class MessagePopUp extends AbstractPopUpView{
		private double startDragX;
	    private double startDragY;
	    private static final double DEFAULT_WIDTH = 350d;
	    private static final double DEFAULT_HEIGHT = 150d;
	    private SimpleDoubleProperty wrapWidth = new SimpleDoubleProperty(DEFAULT_WIDTH-20d);
	    private HBox actionBox;
	    
	    public MessagePopUp(Stage parentStage, String title, String message,double width) {
	    	this(parentStage, title, message);
	    	setWrapWidth(width);
		}

		public MessagePopUp(Stage parentStage, String title, String message){
			super(parentStage,DEFAULT_WIDTH,DEFAULT_HEIGHT);
			super.setPopUpTitle(title);
			super.getScene().getStylesheets().add("styles/alertNconfirm.css");
			super.getScene().setFill(Color.TRANSPARENT);
			super.getStage().initStyle(StageStyle.TRANSPARENT);
			super.getStage().show();
			
			Group mainRoot = new Group();
			StackPane root = new StackPane(){
				@Override
		    	protected void layoutChildren() {
		    		super.layoutChildren();
		    			getStage().setWidth(getWidth()+20);
			    		getStage().setHeight(getHeight()+20);
			    }
			};
			
			root.autosize();
			root.getStyleClass().add("popUpStage");
			mainRoot.getChildren().add(root);
			
	        getRoot().setPadding(new Insets(0,10,10,0));
	        getRoot().getChildren().add(mainRoot);
			
			VBox vb = new VBox();
			/* HEADER */
			HBox header = new HBox();
			header.setAlignment(Pos.CENTER_LEFT);
			header.setPadding(new Insets(0, 2, 0, 5));
			header.getStyleClass().add("popUpHeader");
			header.setPrefHeight(28);
			Button closeBtn = new Button();
		        closeBtn.setId("window-close");
		        closeBtn.setPrefSize(18, 18);
		        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
		            @Override public void handle(ActionEvent actionEvent) {
		                getStage().close();
		            }
		        });
		     StackPane sp = new StackPane();
		     sp.getChildren().add(closeBtn);
		     sp.setAlignment(Pos.CENTER_RIGHT);
		    Label titleLbl = new Label(title);
		    titleLbl.getStyleClass().add("popUpHeaderLbl");
		    
		    header.getChildren().addAll(titleLbl,sp);
		    HBox.setHgrow(sp, Priority.ALWAYS);
		    addDragListeners(header);
		    
		    /* CONTENT */
		    StackPane content = new StackPane();
		    content.setAlignment(Pos.TOP_LEFT);
		    content.setPadding(new Insets(8,5,5,5));
			content.getStyleClass().add("popUpBody");
			content.setMinHeight(50);
		    Text contentTxt = new Text(message);
		    contentTxt.wrappingWidthProperty().bind(this.wrapWidth);
		    content.getChildren().add(contentTxt);
		    VBox.setVgrow(content, Priority.ALWAYS);
		    
		    /* ACTION BOX */
		    actionBox = new HBox();
		    actionBox.setAlignment(Pos.CENTER);
		    actionBox.getStyleClass().add("popUpActionBox");
		    actionBox.setPadding(new Insets(5,0,8,0	));
		    Button okBtn = new Button("Ok");
		    okBtn.setPrefWidth(75);
		    okBtn.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent actionEvent) {
	                getStage().close();
	            }
	        });
		    actionBox.getChildren().add(okBtn);
		    
		    vb.getChildren().addAll(header,content,actionBox);
		    root.getChildren().add(vb);
		    
		}
		
		
		public double getWrapWidth() {
			return wrapWidth.get();
		}

		public void setWrapWidth(double wrapWidth) {
			this.wrapWidth.set(wrapWidth);
		}
		
		public SimpleDoubleProperty wrapWidthProperty() {
			return wrapWidth;
		}

		public HBox getActionBox() {
			return actionBox;
		}

		public void addDragListeners(final Node n){
			n.setOnMousePressed(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	            	    startDragX = me.getSceneX();
	                    startDragY = me.getSceneY();
	                    getRoot().setStyle("-fx-opacity:.7;");
	            }
	        });
			n.setOnMouseReleased(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	            	    getRoot().setStyle("-fx-opacity:1;");
	            }
	        });
	        n.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	            	getStage().setX( me.getScreenX() - startDragX );
	                getStage().setY( me.getScreenY() - startDragY);
	            }
	        });
		 }
	}	
}


