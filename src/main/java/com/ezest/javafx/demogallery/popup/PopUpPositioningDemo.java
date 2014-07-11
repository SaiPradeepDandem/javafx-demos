package com.ezest.javafx.demogallery.popup;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import com.javafx.experiments.scenicview.ScenicView;

public class PopUpPositioningDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	Popup popUp;
	StackPane sp;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		sp = StackPaneBuilder.create().alignment(Pos.TOP_CENTER).maxWidth(850).style("-fx-border-width:1px;-fx-border-color:red;").build();
		root.getChildren().add(sp);
		
		HBox hb = new HBox();
		hb.setStyle("-fx-border-width:0px;-fx-border-color:green;");
		hb.setMaxHeight(75);
		sp.getChildren().add(hb);
		
		StackPane sp1 = StackPaneBuilder.create().alignment(Pos.TOP_CENTER).prefWidth(60).style("-fx-border-width:1px;-fx-border-color:black;").build();
		StackPane sp2 = StackPaneBuilder.create().alignment(Pos.TOP_CENTER).style("-fx-border-width:1px;-fx-border-color:black;").build();
		final StackPane sp3 = StackPaneBuilder.create().prefWidth(60).style("-fx-border-width:1px;-fx-border-color:black;").children(CircleBuilder.create().radius(10).build()).build();
		StackPane sp4 = StackPaneBuilder.create().alignment(Pos.TOP_CENTER).prefWidth(60).style("-fx-border-width:1px;-fx-border-color:black;").build();
		StackPane sp5 = StackPaneBuilder.create().alignment(Pos.TOP_CENTER).prefWidth(60).style("-fx-border-width:1px;-fx-border-color:black;").build();
		
		
		hb.getChildren().addAll(sp1,sp2,sp3,sp4,sp5);
		HBox.setHgrow(sp2, Priority.ALWAYS);
		//ScenicView.show(scene);
		
		sp3.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				showPopUp(sp3);
			}
		});
		sp3.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if(popUp!=null && popUp.isShowing()){
					popUp.hide();
				}
			}
		});
		
		// Logic starts
	}
	
	private Popup getPopUp(){
		if(popUp == null){
			popUp = new Popup();
			popUp.getContent().add(StackPaneBuilder.create().prefWidth(360).prefHeight(150).style("-fx-border-width:1px;-fx-border-color:brown;-fx-background-color:yellow;").build());
		}
		return popUp;
	}

	private void showPopUp(StackPane sp3){
		Popup p = getPopUp();
		//p.show(sp3,0,0);
		showPopupWithinBounds(sp3, p);
		//Point2D pd =  showPopUp(sp3, p);
		//System.out.println(p.getWidth());
		
		//p.show(window, x, y);
	}
	
	public void showPopupBelowNode(final Node node, final Popup popup) {
		final Window window = node.getScene().getWindow();
		double x = window.getX() + node.localToScene(0, 0).getX() + node.getScene().getX();
		double y = window.getY() + node.localToScene(0, 0).getY() + node.getScene().getY() + node.getBoundsInParent().getHeight();
		popup.show(window, x, y);
		if (!popup.getContent().isEmpty()) {
			final Node content = popup.getContent().get(0);
			x -= content.localToScene(0, 0).getX();
			y -= content.localToScene(0, 0).getY();
		}
		Point2D pd= new Point2D(x, y);
		popup.show(window, x, y);
	}
	
	public void showPopupWithinBounds(final Node node, final Popup popup) {
		final Window window = node.getScene().getWindow();
		double x = window.getX() + node.localToScene(0, 0).getX() + node.getScene().getX();
		double y = window.getY() + node.localToScene(0, 0).getY() + node.getScene().getY() + node.getBoundsInParent().getHeight();
		popup.show(window, x, y);
		if (!popup.getContent().isEmpty()) {
			final Node content = popup.getContent().get(0);
			x -= content.localToScene(0, 0).getX();
			y -= content.localToScene(0, 0).getY();
		}
		Point2D pd= new Point2D(x, y);
		
		double Z = window.getX();
		double gX = sp.getLayoutX();
		double c = sp.getWidth()-popup.getWidth();
		
		popup.show(window, (Z+gX+c+8), y);
	}
	
	public Point2D showPopUp(final Node node, final Popup popup){
			final Parent parent = node.getParent();
			final Bounds childBounds = node.getBoundsInParent();

			double x = 0;
			double y = 0;
			if (null != parent) {
				final Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());
				x = childBounds.getMinX() + parentBounds.getMinX() + parent.getScene().getX()
						+ parent.getScene().getWindow().getX();
				y = childBounds.getMaxY() + parentBounds.getMinY() + parent.getScene().getY()
						+ parent.getScene().getWindow().getY();
			}

			Point2D pd= new Point2D(x, y);
			popup.show(node, x, y);
			return pd;
		
	}
	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.setMinWidth(850);
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

}

