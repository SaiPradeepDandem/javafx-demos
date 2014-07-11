package com.ezest.javafx.demogallery.internet;


import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.HTMLEditorBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
*
* @web http://java-buddy.blogspot.com/
*/
public class JavaFX_HTMLeditor_LoadText extends Application {
 

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       launch(args);
   }
    
   @Override
   public void start(final Stage primaryStage) {
       primaryStage.setTitle("java-buddy.blogspot.com");
       Group root = new Group();
        
       final HTMLEditor htmlEditor = HTMLEditorBuilder.create()
               .prefHeight(200)
               .prefWidth(400)
               .build();
        
       final TextArea htmlText = TextAreaBuilder.create()
               .prefWidth(400)
               .wrapText(true)
               .build();
        
       ScrollPane scrollPane = new ScrollPane();
       scrollPane.getStyleClass().add("noborder-scroll-pane");
       scrollPane.setContent(htmlText);
       scrollPane.setFitToWidth(true);
       scrollPane.setPrefHeight(180);
        
       Button buttonUpdate = new Button("Update");
       buttonUpdate.setOnAction(new EventHandler<ActionEvent>(){

           @Override
           public void handle(ActionEvent t) {
               htmlText.setText(htmlEditor.getHtmlText());
           }
       });
        
       Button buttonSave = new Button("Save");
       buttonSave.setOnAction(new EventHandler<ActionEvent>(){

           @Override
           public void handle(ActionEvent t) {
               String stringHtml = htmlEditor.getHtmlText();
               htmlText.setText(stringHtml);
                
               FileChooser fileChooser = new FileChooser();
                
               //Set extension filter
               FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
               fileChooser.getExtensionFilters().add(extFilter);
                
               //Show save file dialog
               File file = fileChooser.showSaveDialog(primaryStage);
               if(file != null){
                   SaveFile(stringHtml, file);
               }
           }
       });
        
       Button buttonLoad = new Button("Load");
       buttonLoad.setOnAction(new EventHandler<ActionEvent>(){

           @Override
           public void handle(ActionEvent arg0) {
               FileChooser fileChooser = new FileChooser();
                
               //Set extension filter
               FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
               fileChooser.getExtensionFilters().add(extFilter);
                
               //Show open file dialog
               File file = fileChooser.showOpenDialog(primaryStage);
               if(file != null){
                   String textRead = readFile(file);
                   htmlEditor.setHtmlText(textRead);
                   htmlText.setText(textRead);
               }
           }
            
       });
        
       HBox buttonBox =HBoxBuilder.create()
               .children(buttonUpdate, buttonSave, buttonLoad)
               .spacing(5)
               .build();

       VBox vBox = VBoxBuilder.create()
               .children(htmlEditor, htmlText, buttonBox)
               .build();
        
       root.getChildren().add(vBox);
       primaryStage.setScene(new Scene(root, 500, 400));
       primaryStage.show();
   }
    
   private void SaveFile(String content, File file){
       try {
           FileWriter fileWriter = null;
            
           fileWriter = new FileWriter(file);
           fileWriter.write(content);
           fileWriter.close();
       } catch (IOException ex) {
           Logger.getLogger(JavaFX_HTMLeditor_LoadText.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    
   private String readFile(File file){
       StringBuilder stringBuffer = new StringBuilder();
       BufferedReader bufferedReader = null;
        
       try {

           bufferedReader = new BufferedReader(new FileReader(file));
            
           String text;
           while ((text = bufferedReader.readLine()) != null) {
               stringBuffer.append(text);
           }

       } catch (FileNotFoundException ex) {
           Logger.getLogger(JavaFX_HTMLeditor_LoadText.class.getName()).log(Level.SEVERE, null, ex);
       } catch (IOException ex) {
           Logger.getLogger(JavaFX_HTMLeditor_LoadText.class.getName()).log(Level.SEVERE, null, ex);
       } finally {
           try {
               bufferedReader.close();
           } catch (IOException ex) {
               Logger.getLogger(JavaFX_HTMLeditor_LoadText.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
        
       return stringBuffer.toString();
   }
    
}
