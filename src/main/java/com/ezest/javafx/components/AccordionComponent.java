package com.ezest.javafx.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class AccordionComponent extends VBox {
	
	private Accordion accordion;

	public AccordionComponent() {
		super.setPadding(new Insets(0, 10, 10, 10));
		super.setFillWidth(true);
		super.setPrefWidth(200);
		super.setPrefHeight(450);
		this.accordion = new Accordion();	
		this.getChildren().add(this.accordion);
	}
	
	public AccordionPane createPane(String title,boolean expanded) {
		AccordionPane p = new AccordionPane(title);
		accordion.getPanes().add(p);
		if(expanded){
			this.accordion.setExpandedPane(p);
		}
		return p;
	}
	
	public class AccordionPane extends TitledPane{
		private String title;
		
		public AccordionPane(final String title) {
			//super.setTitle(new Label(title));
			
			final VBox vbox = new VBox();
			vbox.setPadding(new Insets(10, 10, 10, 10));
			vbox.setSpacing(10);
			super.setContent(vbox);
			
			super.expandedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0,
						Boolean arg1, Boolean arg2) {
					if(arg2){
						System.out.println(title +"  expanded");
					}
				}
			});
			
			
		}
		
		
		
	}
	
}
