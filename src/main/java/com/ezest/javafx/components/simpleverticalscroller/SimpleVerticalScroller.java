package com.ezest.javafx.components.simpleverticalscroller;

import java.util.List;

import javax.xml.ws.handler.MessageContext.Scope;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;

public class SimpleVerticalScroller extends VBox{
	private SimpleBooleanProperty topButtonDisabled = new SimpleBooleanProperty();
	private SimpleBooleanProperty bottomButtonDisabled = new SimpleBooleanProperty();
	private StackPane topButton = StackPaneBuilder.create().minHeight(30).prefHeight(30).style("-fx-background-color:red;").build();
	private StackPane bottomButton = StackPaneBuilder.create().minHeight(30).prefHeight(30).style("-fx-background-color:red;").build();
	private VBox scrollBody = VBoxBuilder.create().build();
	private StackPane scrollContainer = StackPaneBuilder.create().children(scrollBody).build();
	
	private SimpleListProperty<SimpleVerticalScrollerItem> items = new SimpleListProperty<SimpleVerticalScrollerItem>(FXCollections.<SimpleVerticalScrollerItem>observableArrayList());
	
	public SimpleVerticalScroller(){
		super();
		setMinWidth(200);
		getChildren().addAll(topButton, scrollContainer, bottomButton);
		VBox.setVgrow(scrollContainer, Priority.ALWAYS);
		
		items.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable arg0) {
				scrollBody.getChildren().clear();
				for (SimpleVerticalScrollerItem item : items) {
					scrollBody.getChildren().add(item);
				}
			}
		});
	}
	
	public SimpleListProperty<SimpleVerticalScrollerItem> itemsProperty() {
		return items;
	}
	
	public List<SimpleVerticalScrollerItem> getItems() {
		return items.get();
	}
	
	public void setItems(List<SimpleVerticalScrollerItem> items) {
		this.items.setAll(items);
	}
	
	public SimpleBooleanProperty topButtonDisabledProperty() {
		return topButtonDisabled;
	}
	
	public SimpleBooleanProperty bottomButtonDisabledProperty() {
		return bottomButtonDisabled;
	}
	
	public StackPane getTopButton() {
		return topButton;
	}
	
	public StackPane getBottomButton() {
		return bottomButton;
	}
	
}
