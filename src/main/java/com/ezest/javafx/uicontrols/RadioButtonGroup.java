package com.ezest.javafx.uicontrols;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class RadioButtonGroup {
	
public static Node getNode(){
		
		GridPane gp = new GridPane();
		
		Image image = new Image(RadioButtonGroup.class.getResourceAsStream("/images/home.png"));
		final ImageView imageView = new ImageView( new Image(RadioButtonGroup.class.getResourceAsStream("/images/home.png")));
		
		
		VBox vb = new VBox();
		vb.setPadding(new Insets(10, 10, 10, 10));
		
		final ToggleGroup tg = new ToggleGroup();
		
		
		RadioButton rb1 = new RadioButton("Home");
		rb1.setToggleGroup(tg);
		rb1.setSelected(true);
		rb1.setUserData("home");
		
		vb.getChildren().add(rb1);
		
		RadioButton rb2 = new RadioButton("Calendar");
		rb2.setToggleGroup(tg);
		rb2.setUserData("calendar");
		vb.getChildren().add(rb2);
		rb2.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				System.out.println(" Focused : "+arg2);
			}
		});
		 
		RadioButton rb3 = new RadioButton("Contacts");
		rb3.setToggleGroup(tg);
		rb3.setUserData("dairy");
		vb.getChildren().add(rb3);
		
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				if(tg.getSelectedToggle()!=null){
					imageView.setImage(  new Image(getClass().getResourceAsStream("/images/"+tg.getSelectedToggle().getUserData().toString()+".png")) );
				}
			}
		});
		
		gp.add(vb, 0, 0);
		gp.add(imageView, 1, 0);
		
		return gp;
	}
}
