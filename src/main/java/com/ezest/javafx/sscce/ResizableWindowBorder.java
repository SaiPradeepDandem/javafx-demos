package com.ezest.javafx.sscce;

import com.sun.javafx.Utils;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ResizableWindowBorder extends Application {

	private WindowResizeButton windowResizeButton;
	
	@Override
	public void start(Stage stage) throws Exception {
		windowResizeButton = new WindowResizeButton(stage, 500,500);
		StackPane root = new StackPane(){
			 @Override protected void layoutChildren() {
	                super.layoutChildren();
	                windowResizeButton.autosize();
	                windowResizeButton.setLayoutX(getWidth()-(windowResizeButton.getLayoutBounds().getWidth()+2));
	                windowResizeButton.setLayoutY(getHeight()-(windowResizeButton.getLayoutBounds().getHeight()+2));
	            }
		};
		root.autosize();
		root.setStyle( new StringBuilder().append("-fx-background-radius: 8, 8, 8, 8;")
										  .append("-fx-border-radius: 8, 8, 8, 8;")
										  .append("-fx-background-color: #336699;")
										  .append("-fx-border-color: #6e737d , rgba(255,255,255,0.3);")
										  .append("-fx-border-insets: 0,1;")
										  .append("-fx-border-width:2px;")
										  .append("-fx-padding:5px;").toString());
		root.getChildren().add( windowResizeButton );
		
		Scene scene = new Scene(root, 500, 500, Color.TRANSPARENT);
		stage.setWidth(getScreenBounds().getWidth()-300);
	    stage.setHeight(getScreenBounds().getHeight()-100);
	    stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("JavaFx Layout Demo");
		stage.setScene(scene);
		stage.show();
	}

	public Rectangle2D getScreenBounds(){
		Screen screen = Screen.getPrimary();
	    Rectangle2D bounds = screen.getVisualBounds();
	    return bounds;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	class WindowResizeButton extends Region {
	    private double dragOffsetX, dragOffsetY;

	    public WindowResizeButton(final Stage stage, final double stageMinimumWidth, final double stageMinimumHeight) {
	        setPrefSize(11,11);
	        setCursor(Cursor.SE_RESIZE);
	        setStyle( new StringBuilder().append("-fx-background-image: url(\"window-corner.png\");")
									     .append("-fx-background-position: right 1px bottom 1px;")
									     .append("-fx-background-repeat: no-repeat;").toString());
					        
	        setOnMousePressed(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent e) {
	                final double stageY = Utils.isMac() ? stage.getY() + 22 : stage.getY(); 
	                dragOffsetX = (stage.getX() + stage.getWidth()) - e.getScreenX();
	                dragOffsetY = (stageY + stage.getHeight()) - e.getScreenY();
	                e.consume();
	            }
	        });
	        setOnMouseDragged(new EventHandler<MouseEvent>() {
	            public void handle(MouseEvent e) {
	                final double stageY = Utils.isMac() ? stage.getY() + 22 : stage.getY(); 
	                final Screen screen = Screen.getScreensForRectangle(stage.getX(), stageY, 1, 1).get(0);
	                Rectangle2D visualBounds = screen.getVisualBounds();
	                if (Utils.isMac()) visualBounds = new Rectangle2D(visualBounds.getMinX(), visualBounds.getMinY() + 22,
	                        visualBounds.getWidth(), visualBounds.getHeight()); 
	                double maxX = Math.min(visualBounds.getMaxX(), e.getScreenX() + dragOffsetX);
	                double maxY = Math.min(visualBounds.getMaxY(), e.getScreenY() - dragOffsetY);
	                stage.setWidth(Math.max(stageMinimumWidth, maxX - stage.getX()));
	                stage.setHeight(Math.max(stageMinimumHeight, maxY - stageY));
	                e.consume();
	            }
	        });
	    }
	}
}
