package com.ezest.javafx.uicontrols;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ToggleButtonGroup {
	
	public static Node getNode(){
		VBox vb = new VBox();
		
		HBox hb = new HBox();
		
		final ToggleGroup tg = new ToggleGroup();
		
		ToggleButton tb1 = new ToggleButton("Blue");
		tb1.setUserData(Color.BLUE);
		tb1.setToggleGroup(tg);
		hb.getChildren().add(tb1);
		
		ToggleButton tb2 = new ToggleButton("Green");
		tb2.setUserData(Color.GREEN);
		tb2.setToggleGroup(tg);
		hb.getChildren().add(tb2);
		
		ToggleButton tb3 = new ToggleButton("Yellow");
		tb3.setUserData(Color.YELLOW);
		tb3.setToggleGroup(tg);
		hb.getChildren().add(tb3);
		
		final Rectangle rect = new Rectangle(240, 100);
		
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if(tg.selectedToggleProperty()!=null){
					rect.setFill( (Paint)tg.getSelectedToggle().getUserData());
				}
			}
		});
		vb.getChildren().add(hb);
		vb.getChildren().add(rect);
		
		
		return vb;
	}
	
	public static Node getNode2(){
		VBox vb = new VBox();
		
		HBox hb = new HBox();
		
		final ToggleGroup tg = new ToggleGroup();
		
		ToggleButton tb1 = new ToggleButton("Blue");
		tb1.setUserData(new Text("Blue"));
		tb1.setToggleGroup(tg);
		hb.getChildren().add(tb1);
		
		ToggleButton tb2 = new ToggleButton("Green");
		tb2.setUserData(new Text("Green"));
		tb2.setToggleGroup(tg);
		hb.getChildren().add(tb2);
		
		ToggleButton tb3 = new ToggleButton("Yellow");
		tb3.setUserData(new Text("Yellow"));
		tb3.setToggleGroup(tg);
		hb.getChildren().add(tb3);
		
		
		final ScrollPane sp = new ScrollPane();
		sp.setPrefHeight(100);
		sp.setPrefWidth(100);
		final Rectangle rect = new Rectangle(240, 100);
		
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if(tg.selectedToggleProperty()!=null){
					sp.setContent((Text)tg.getSelectedToggle().getUserData());
				}
			}
		});
		vb.getChildren().add(hb);
		vb.getChildren().add(sp);
		
		
		return vb;
	}
}
