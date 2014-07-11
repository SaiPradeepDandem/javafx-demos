package com.ezest.javafx.demogallery.controls;

/**

 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.

 * All rights reserved. Use is subject to license terms.

 */

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

 

/**

 * This sample demonstrates styling toggle buttons with CSS.

 *

 * @see javafx.scene.control.ToggleButton

 * @related controls/buttons/ToggleButton

 * @resource PillButton.css

 * @resource center-btn.png

 * @resource center-btn-selected.png

 * @resource left-btn.png

 * @resource left-btn-selected.png

 * @resource right-btn.png

 * @resource right-btn-selected.png

 */

public class PillButtonSample extends Application {

	Scene scene;
	VBox root;

    private void init(Stage primaryStage) {

        root = VBoxBuilder.create().padding(new Insets(15)).spacing(10).build();
        scene = new Scene(root,1000,500);
        scene.getStylesheets().add("styles/pillbutton/PillButton.css");
        primaryStage.setScene(scene);

 

        //String pillButtonCss = PillButtonSample.class.getResource("PillButton.css").toExternalForm();

 

        // create 3 toggle buttons and a toogle group for them

        final ToggleButton tb1 = new ToggleButton("Left Button Test");
        tb1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(!tb1.isSelected()){
					tb1.setSelected(true);
				}
			}
		});
        tb1.getStyleClass().add("pill-left");

        ToggleButton tb2 = new ToggleButton("Center Button Test");

        tb2.getStyleClass().add("pill-center");

        ToggleButton tb3 = new ToggleButton("Right Button Test");

        tb3.getStyleClass().add("pill-right");

 

        ToggleGroup group = new ToggleGroup();

        tb1.setToggleGroup(group);

        tb2.setToggleGroup(group);

        tb3.setToggleGroup(group);

        // select the first button to start with

        group.selectToggle(tb1);

 

        HBox hBox = new HBox();
        hBox.getChildren().addAll(tb1, tb2, tb3);
        root.getChildren().addAll(new Label("Using ToggleButtons"),hBox);
        final CheckBox cb1 = new CheckBox();
        final CheckBox cb2= new CheckBox();
        final CheckBox cb3 = new CheckBox();
        
        final List<CheckBox> list = new ArrayList<CheckBox>();
        list.add(cb1);
        list.add(cb2);
        list.add(cb3);
        
        Label lbl1 = new Label("Select All");
        Label lbl2 = new Label("De Select All");
        lbl1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				for (CheckBox checkBox : list) {
					checkBox.setSelected(true);
				}
			}
		});
        
        
        lbl2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				for (CheckBox checkBox : list) {
					checkBox.setSelected(false);
				}
			}
		});
         root.getChildren().addAll(lbl1,lbl2,cb1,cb2,cb3);
        

    }

 

    @Override public void start(Stage primaryStage) throws Exception {

    	init(primaryStage);
    	initSecond();
        primaryStage.show();
        ScenicView.show(scene);

    }

    private void initSecond() {
    	
    	 final Button tb1 = new Button("Left Button TestLeft Button TestLeft Button Test");
    	 tb1.getStyleClass().addAll("pill-left-btn");

    	 final Button tb2 = new Button("Center Button TestCenter1");
         tb2.getStyleClass().addAll("pill-center-left-btn");

         final Button tb3 = new Button("Center Button TestCenter2");
         tb3.getStyleClass().addAll("pill-center-btn");

         final Button tb4 = new Button("Right Button TestRight Button TestRight Button Test");
         tb4.getStyleClass().addAll("pill-right-btn");

         EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
 			@Override
 			public void handle(MouseEvent arg0) {
 				tb1.getStyleClass().remove("selected");
 				tb2.getStyleClass().remove("selected");
 				tb3.getStyleClass().remove("selected");
 				tb4.getStyleClass().remove("selected");
 				Button btn = (Button)arg0.getSource();
 				btn.getStyleClass().add("selected");
 			}
 		};
 		
 		tb1.setOnMouseClicked(event);
 		tb2.setOnMouseClicked(event);
 		tb3.setOnMouseClicked(event);
 		tb4.setOnMouseClicked(event);
 		
         HBox hBox = new HBox();
         hBox.getChildren().addAll(tb1, tb2, tb3 ,tb4);
         root.getChildren().addAll(new Label("Using Buttons"),hBox);
	}



	public static void main(String[] args) { launch(args); }

}
