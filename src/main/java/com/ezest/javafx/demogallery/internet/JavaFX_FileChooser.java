package com.ezest.javafx.demogallery.internet;

import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
/**
*
* @author Seven
*/
public class JavaFX_FileChooser extends Application {
   
   File file;
   ListView<String> listView;
   ObservableList<String> list;
   List<File> fileList;
 
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       launch(args);
   }
   
   @Override
   public void start(final Stage primaryStage) {
       primaryStage.setTitle("Hello World!");
       
       listView = new ListView<String>();
       list =FXCollections.observableArrayList ();
       
       final Label labelFile = new Label();
       
       Button btn = new Button();
       btn.setText("Open FileChooser'");
       btn.setOnAction(new EventHandler<ActionEvent>() {
 
           @Override
           public void handle(ActionEvent event) {
               FileChooser fileChooser = new FileChooser();
               
               //Open directory from existing directory
               if( fileList != null ){
                   if( !fileList.isEmpty() ){
                       File existDirectory = fileList.get(0).getParentFile();
                       fileChooser.setInitialDirectory(existDirectory);
                   }
               }
              
               //Set extension filter
               FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("AVI files (*.avi)", "*.avi");
               FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("WMV files (*.wmv)", "*.wmv");
               FileChooser.ExtensionFilter extFilter3 = new FileChooser.ExtensionFilter("All files (*.*)", "*.*");
               fileChooser.getExtensionFilters().addAll(extFilter1,extFilter2,extFilter3);
               
               //Show open file dialog, with primaryStage blocked.
               
               // Sets the owner window to disable.
               fileList = fileChooser.showOpenMultipleDialog(primaryStage);
               
               // Allows the owner window to be accessble.
               //fileList = fileChooser.showOpenMultipleDialog(null);
                
               list.clear();
               for(int i=0; i<fileList.size(); i++){
                   list.add(fileList.get(i).getPath());
               }
               
               listView.setItems(list);
               
           }
       });
       
       VBox vBox = new VBox();
       vBox.getChildren().addAll(labelFile, btn);
       
       vBox.getChildren().add(listView);
       
       StackPane root = new StackPane();
       root.getChildren().add(vBox);
       primaryStage.setScene(new Scene(root, 300, 250));
       primaryStage.show();
   }
}
