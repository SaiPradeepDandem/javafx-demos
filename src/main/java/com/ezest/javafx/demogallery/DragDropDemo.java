package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DragDropDemo extends Application {

	Stage stage;
	Scene scene;
	Group root;
	StackPane stackDesk;
	ScrollPane scrollDesk;
	StackPane paneDesk;
	double posX=0,posY=0;
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configureDesk();
		//configurePanes();
		
	}

	private void configureScene(){
		this.root = new Group();
		root.autosize();
		this.scene = new Scene(root, Color.DIMGREY);
		scene.getStylesheets().add("styles/dragdrop_styles.css");
	}
	
	private void configureStage(){
		stage.setTitle("Drag and Drop Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureDesk(){
		this.stackDesk = new StackPane();
		stackDesk.setAlignment(Pos.TOP_LEFT);
		stackDesk.setPrefWidth( this.scene.getWidth()-250);
		stackDesk.setPrefHeight( this.scene.getHeight()-150);
		
		this.scrollDesk = new ScrollPane();
		scrollDesk.setPrefWidth( this.scene.getWidth()-250);
		scrollDesk.setPrefHeight( this.scene.getHeight()-150);
		scrollDesk.getStyleClass().add("deskPane");
		scrollDesk.setContent( new PaneRegion());
        
		stackDesk.setLayoutX((this.scene.getWidth() - scrollDesk.getPrefWidth()) / 2);
		stackDesk.setLayoutY((this.scene.getHeight() - scrollDesk.getPrefHeight()) / 2);
		stackDesk.getChildren().add(scrollDesk);
        
        Group grp = new Group();
        HBox addBtn = new HBox(3);
        addBtn.setPrefSize(100, 30);
        addBtn.getStyleClass().add("addButton");
        grp.getChildren().add(addBtn);
        
        grp.setTranslateX( scrollDesk.getPrefWidth()-addBtn.getPrefWidth()-2);
        grp.setTranslateY( scrollDesk.getPrefHeight()-addBtn.getPrefHeight()-2);
        
        
        stackDesk.getChildren().add(grp);
        
		this.root.getChildren().add(stackDesk);
	}
	private void configurePanes(){
		this.paneDesk = new StackPane();
		paneDesk.setAlignment(Pos.TOP_LEFT);
		
		
		for(int i=0;i<15;i++){
			MyPane pane = new MyPane(250, "Hello",i);
			adjustPanePosition(pane);
			paneDesk.getChildren().add(pane);
		}
		
		this.scrollDesk.setContent(paneDesk);
		//adjustPanesPosition();
	}
	
	private void adjustPanePosition(MyPane pane){
		double deskWidth = scrollDesk.getPrefWidth();
		double paneWidth= ((VBox)pane.getChildren().get(0)).getPrefWidth();
		double paneHeight= ((VBox)pane.getChildren().get(0)).getPrefHeight();
		
			if(posX>deskWidth || (posX+paneWidth)>deskWidth){
				posY = posY +paneHeight;
				posX = 0;
			}
			pane.setTranslateX(posX);
			pane.setTranslateY(posY);
			//System.out.println(deskWidth+" : "+paneWidth+" : "+paneHeight);
			//System.out.println(posX+" : "+posY);
			posX = posX+paneWidth;
	}
		
	private void adjustPanesPosition(){
		ObservableList<Node> paneList = this.paneDesk.getChildren();
		double deskWidth = scrollDesk.getPrefWidth();
		double paneWidth;
		double paneHeight;
		posX=0;posY=0;
		
		for (Node node : paneList) {
			paneWidth = ((VBox)((MyPane)node).getChildren().get(0)).getPrefWidth();
			paneHeight = ((VBox)((MyPane)node).getChildren().get(0)).getPrefHeight();
			
			if(posX>deskWidth || (posX+paneWidth)>deskWidth){
				posY = posY +paneHeight;
				posX = 0;
			}
			node.setTranslateX(posX);
			node.setTranslateY(posY);
			//System.out.println(deskWidth+" : "+paneWidth+" : "+paneHeight);
			//System.out.println(posX+" : "+posY);
			posX = posX+paneWidth;
		}
	}

}// eo class DragDropDemo

class PaneRegion extends TilePane{
	public PaneRegion(){
		//setPrefColumns(2);
		//setPrefHeight(200);
		//setPrefWidth(800);
		setStyle("-fx-background-color:aqua;");
		for(int i=0;i<15;i++){
			MyPane pane = new MyPane(250, "Hello",i);
			Text txt = new Text("Checking");
			//txt.setTranslateY(100*i);
			getChildren().add(pane);
			
		}
	}
}
class MyPane extends Group{
	final VBox vb = new VBox();
	private double startDragX;
    private double startDragY;
    private Point2D dragAnchor;

	public MyPane(double width, String title, int count){
		super();
		vb.setPrefWidth(width);
		vb.setPrefHeight(180);
		vb.getStyleClass().add("dragPane");
		//getStyleClass().add("dragGroup");
		
			HBox paneTitle = new HBox(3);
			paneTitle.getStyleClass().add("paneTitle");
			paneTitle.setPrefHeight(25);
			
				Label lblTitle = new Label(title);
				lblTitle.setTranslateX(3);
				lblTitle.setTranslateY(3);
			paneTitle.getChildren().add(lblTitle);
		
			Text txt = new Text(count+"");
			txt.setFont(new Font("Tahoma", 26));
			txt.setTranslateX(120);
			txt.setTranslateY(55);
			
		vb.getChildren().addAll(paneTitle,txt);
		
		
		addListeners();
		addDragListeners(paneTitle);
		getChildren().add(vb);
	}
	
	 public void addListeners() {
	        setOnMouseMoved(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	            	DropShadow ds = new DropShadow();
	            	ds.setSpread(0.3);
	            	ds.setOffsetX(3.0);
	            	ds.setOffsetY(3.0);
	            	ds.setBlurType(BlurType.GAUSSIAN);
	            	ds.setColor(Color.GRAY);
	            	setEffect(ds);
	            }
	        });
	        
	        setOnMouseExited(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	            	setEffect(null);
	            }
	        });
	        
	 }
	 
	 public void addDragListeners(final Node n){
		 n.setOnMousePressed(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	                    toFront();
	                    startDragX = getTranslateX();
	                    startDragY = getTranslateY();
	                    dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
	            }
	        });
	        n.setOnMouseReleased(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	                    if (getTranslateX() < (10) && getTranslateX() > (- 10) &&
	                        getTranslateY() < (10) && getTranslateY() > (- 10)) {
	                        setTranslateX(0);
	                        setTranslateY(0);
	                        //setInactive();
	                }
	            }
	        });
	        n.setOnMouseDragged(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent me) {
	                double newTranslateX = startDragX
	                                        + me.getSceneX() - dragAnchor.getX();
	                double newTranslateY = startDragY 
	                                        + me.getSceneY() - dragAnchor.getY();
	                /*float minTranslateX = - 45f - correctX;
	                float maxTranslateX = (Desk.DESK_WIDTH - Piece.SIZE + 50f ) - correctX;
	                float minTranslateY = - 30f - correctY;
	                float maxTranslateY = (Desk.DESK_HEIGHT - Piece.SIZE + 70f ) - correctY;

	                if ((newTranslateX> minTranslateX ) &&
	                    (newTranslateX< maxTranslateX) &&
	                    (newTranslateY> minTranslateY) &&
	                    (newTranslateY< maxTranslateY))
	                {*/
	                    setTranslateX(newTranslateX);
	                    setTranslateY(newTranslateY);
	                //}
	            }
	        });
	 }
	
} // eo class MyPane
