package com.ezest.javafx.sscce;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SliderIncrement extends Application{
	
	private Parent getContent() {
        
        final Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setSnapToTicks(true);
 
        String duration = "3000ms"; // Change this value for different speeds
        
        final Timeline timeline = new Timeline();
		timeline.setCycleCount(1); 
		KeyValue kv = new KeyValue(slider.valueProperty(),100);
		KeyFrame kf = new KeyFrame(Duration.valueOf(duration), kv);
		timeline.getKeyFrames().add(kf);
		
        Button button = new Button("Button");
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                timeline.play();
            }
        });
        
        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(slider, button);
        return vbox;
    }
    
    public void start(final Stage stage) throws Exception {  
        Scene scene = new Scene(getContent(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
		Application.launch(args);
	}
}
