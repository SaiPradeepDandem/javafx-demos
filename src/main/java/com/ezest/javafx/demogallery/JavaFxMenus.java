package com.ezest.javafx.demogallery;

import static java.lang.System.out;  

import javafx.application.Application;  
import javafx.beans.property.ReadOnlyDoubleProperty;  
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.control.*;  
import javafx.scene.input.KeyCode;  
import javafx.scene.input.KeyCodeCombination;  
import javafx.scene.input.KeyCombination;  
import javafx.scene.paint.Color;  
import javafx.stage.Stage;  
  
/** 
 * Example of creating menus in JavaFX. 
 *  
 * @author Dustin 
 */  
public class JavaFxMenus extends Application  
{  
   /** 
    * Build menu bar with included menus for this demonstration. 
    *  
    * @param menuWidthProperty Width to be bound to menu bar width. 
    * @return Menu Bar with menus included. 
    */  
   private MenuBar buildMenuBarWithMenus(final ReadOnlyDoubleProperty menuWidthProperty)  
   {  
      final MenuBar menuBar = new MenuBar();  
  
      // Prepare left-most 'File' drop-down menu  
      final Menu fileMenu = new Menu("File");  
      fileMenu.getItems().add(new MenuItem("New"));  
      fileMenu.getItems().add(new MenuItem("Open"));  
      fileMenu.getItems().add(new MenuItem("Save"));  
      fileMenu.getItems().add(new MenuItem("Save As"));  
      fileMenu.getItems().add(new SeparatorMenuItem());  
      fileMenu.getItems().add(new MenuItem("Exit"));  
      menuBar.getMenus().add(fileMenu);  
  
      // Prepare 'Examples' drop-down menu  
      final Menu examplesMenu = new Menu("JavaFX 2.0 Examples");  
      examplesMenu.getItems().add(new MenuItem("Text Example"));  
      examplesMenu.getItems().add(new MenuItem("Objects Example"));  
      examplesMenu.getItems().add(new MenuItem("Animation Example"));  
      menuBar.getMenus().add(examplesMenu);  
  
      // Prepare 'Help' drop-down menu  
      final Menu helpMenu = new Menu("Help");  
      final MenuItem searchMenuItem = new MenuItem("Search");  
      searchMenuItem.setDisable(true);  
      helpMenu.getItems().add(searchMenuItem);  
      final MenuItem onlineManualMenuItem = new MenuItem("Online Manual");  
      onlineManualMenuItem.setVisible(false);  
      helpMenu.getItems().add(onlineManualMenuItem);  
      helpMenu.getItems().add(new SeparatorMenuItem());  
      final MenuItem aboutMenuItem =  
         MenuItemBuilder.create()  
                        .text("About")  
                        .onAction(  
                            new EventHandler<ActionEvent>()  
                            {  
                               @Override public void handle(ActionEvent e)  
                               {  
                                  out.println("You clicked on About!");  
                               }  
                            })  
                        .accelerator(  
                            new KeyCodeCombination(  
                               KeyCode.A, KeyCombination.CONTROL_DOWN))  
                        .build();               
      helpMenu.getItems().add(aboutMenuItem);  
      menuBar.getMenus().add(helpMenu);  
  
      // bind width of menu bar to width of associated stage  
      menuBar.prefWidthProperty().bind(menuWidthProperty);  
  
      return menuBar;  
   }  
  
   /** 
    * Start of JavaFX application demonstrating menu support. 
    *  
    * @param stage Primary stage. 
    */  
   @Override  
   public void start(final Stage stage)  
   {  
      stage.setTitle("Creating Menus with JavaFX 2.0");  
      final Group rootGroup = new Group();  
      final Scene scene = new Scene(rootGroup, 800, 400, Color.WHEAT);  
      final MenuBar menuBar = buildMenuBarWithMenus(stage.widthProperty());  
      rootGroup.getChildren().add(menuBar);  
      stage.setScene(scene);  
      stage.show();  
   }  
  
   /** 
    * Main executable function for running examples. 
    *  
    * @param arguments Command-line arguments: none expected. 
    */  
   public static void main(final String[] arguments)  
   {  
      Application.launch(arguments);  
   }  
}  
