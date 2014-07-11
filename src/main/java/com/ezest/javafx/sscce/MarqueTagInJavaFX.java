package com.ezest.javafx.sscce;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MarqueTagInJavaFX extends Application
{
   
   private void applyAnimation(final StackPane group)
   {
	   
	   DropShadow ds = new DropShadow();
	   ds.setOffsetY(3.0f);
	   ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
	    
	   Text t = new Text();
	   t.setEffect(ds);
	   t.setCache(true);
	   t.setFill(Color.BLUE);
	   t.setText("JavaFX drop shadow...");
	   t.setFont(Font.font(null, FontWeight.BOLD, 32));
	   
	   final TranslateTransition animation = new TranslateTransition(Duration.millis(2000), t);
       animation.setFromX(-100);
       animation.setToX(100);
       animation.setAutoReverse(true);
       animation.setCycleCount(Animation.INDEFINITE);
      
       TranslateTransition animation1 = new TranslateTransition(Duration.millis(1000), t);
       animation1.setFromX(0);
       animation1.setToX(-100);
       animation1.setAutoReverse(false);
       animation1.setCycleCount(1);
       animation1.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				 animation.playFromStart();
			}
		});
       animation1.playFromStart();
       
       group.getChildren().add(t);
    }

   
   @Override
   public void start(final Stage stage) throws Exception
   {
      final StackPane rootGroup = new StackPane();
      rootGroup.setAlignment(Pos.TOP_LEFT);
      final Scene scene = new Scene(rootGroup, 600, 400, Color.GHOSTWHITE);
      stage.setScene(scene);
      stage.setTitle("MARQUE TEXT");
      stage.show();
      applyAnimation(rootGroup);
   }

  
   public static void main(final String[] arguments)
   {
      Application.launch(arguments);
   }
}