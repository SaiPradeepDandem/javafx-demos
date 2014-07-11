package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
 
/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFX_VideoPlayer extends Application {
     
    private static final String MEDIA_URL = "D:\\Pradeep\\Funny Mails\\AwesomeMischief.wmv";//"http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
     private static String arg1;
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("java-buddy.blogspot.com/");
         
        Group root = new Group();
        Scene scene = new Scene(root, 540, 210);
 
        // create media player
        Media media = new Media((arg1 != null) ? arg1 : MEDIA_URL);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
         
        // create mediaView and add media player to the viewer
        MediaView mediaView = new MediaView(mediaPlayer);
        ((Group)scene.getRoot()).getChildren().add(mediaView);
         
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}