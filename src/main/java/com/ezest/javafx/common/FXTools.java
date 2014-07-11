package com.ezest.javafx.common;

import java.awt.Toolkit;
import java.net.URL;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Screen;

public class FXTools {

	private FXTools(){
		throw new AssertionError();
	}
	public static double getAppWidth() {
		return Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	public static double getAppHeight() {
		return Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}
	
	public static Rectangle2D getScreenBounds() {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		return bounds;
	}
	
	public static String getResource(String file) {
		URL resource =  FXTools.class.getResource(file);
		return resource == null ? file : resource.toExternalForm();
	}
	
	public static String getImageUrl(String imageName){
		return FXTools.class.getResource("/images/"+imageName).toExternalForm();
	}
	
	public static Point2D getRelativeStartPointOfNode(Node n){
		return new Point2D(n.localToScene(n.getBoundsInLocal()).getMinX(), 
				           n.localToScene(n.getBoundsInLocal()).getMinY());
	}
	
	public static Point2D getAbsoluteStartPointOfNode(Node n){
		Parent parent = n.getParent();
        Bounds childBounds = n.getBoundsInParent();
        Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());
        double x = childBounds.getMinX() + parentBounds.getMinX() + parent.getScene().getX() + parent.getScene().getWindow().getX();
        double y = childBounds.getMaxY() + parentBounds.getMinY() + parent.getScene().getY() + parent.getScene().getWindow().getY();
        return new Point2D(x, y);
	}
}
