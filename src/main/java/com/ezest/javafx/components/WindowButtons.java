package com.ezest.javafx.components;

import com.sun.javafx.Utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class WindowButtons extends Region{
	public WindowButtons(Stage stage){
		setPrefSize(45,18);
		getChildren().add( new WindowControlButtons(stage));
	}
}

class WindowControlButtons extends HBox{

    private Rectangle2D backupWindowBounds;

    public WindowControlButtons(final Stage stage) {
        super(4); // Setting spacing.
        
        // create buttons
         Button closeBtn = new Button();
        closeBtn.setId("window-close");
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                //Platform.exit();
                stage.close();
            }
        });
        Button minBtn = new Button();
        minBtn.setId("window-min");
        minBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                stage.setIconified(true);
            }
        });
        Button maxBtn = new Button();
        maxBtn.setId("window-max");
        maxBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                final double stageY = Utils.isMac() ? stage.getY() - 22 : stage.getY(); 
                final Screen screen = Screen.getScreensForRectangle(stage.getX(), stageY, 1, 1).get(0);
                Rectangle2D bounds = screen.getVisualBounds();
                if (bounds.getMinX() == stage.getX() && bounds.getMinY() == stageY &&
                        bounds.getWidth() == stage.getWidth() && bounds.getHeight() == stage.getHeight()) {
                    if (backupWindowBounds != null) {
                        stage.setX(backupWindowBounds.getMinX());
                        stage.setY(backupWindowBounds.getMinY());
                        stage.setWidth(backupWindowBounds.getWidth());
                        stage.setHeight(backupWindowBounds.getHeight());
                    }
                } else {
                    backupWindowBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
                    final double newStageY = Utils.isMac() ? screen.getVisualBounds().getMinY() + 22 : screen.getVisualBounds().getMinY(); // TODO Workaround for RT-13980
                    stage.setX(screen.getVisualBounds().getMinX());
                    stage.setY(newStageY);
                    stage.setWidth(screen.getVisualBounds().getWidth());
                    stage.setHeight(screen.getVisualBounds().getHeight());
                }
            }
        });
        getChildren().addAll(closeBtn, minBtn, maxBtn);
    }
}
