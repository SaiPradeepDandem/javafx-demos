package com.ezest.javafx.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import com.sun.javafx.scene.control.skin.LabelSkin;

public class TabPaneComponent extends TabPane{

	public TabPaneComponent(){
		super();
		//super.autosize();
	}
	public TabPaneComponent(double width, double height){
		super();
		super.resize(width, height);
	}
	
	public Tab createNewTab(boolean closable, String title) {
		Tab tab = new Tab(title);
		if(closable){
			setClosableButton(tab);
		}
		tab.setClosable(false);
		Tooltip tooltip = new Tooltip( "Hello" );
		tab.setTooltip(tooltip);
		tab.setId(title);
		
		tab.setOnClosed(new EventHandler<javafx.event.Event>() {
			public void handle(javafx.event.Event e) {
				// TODO: Actions to be taken on close of tab.
			}
		});
		
		getTabs().add(tab);
		
		// When a new tab is added, selecting the new tab by default.
		selectTab(tab);
		return tab;
	}
	
	private void setClosableButton(final Tab tab){
		final StackPane closeBtn = new StackPane(){
			@Override
			protected void layoutChildren() {
				super.layoutChildren();
				((Label)((LabelSkin)getParent()).getSkinnable()).setStyle("-fx-content-display:right;");
			}
		};
		closeBtn.getStyleClass().setAll(new String[] { "tab-close-button" });
		closeBtn.setStyle("-fx-cursor:hand;");
		closeBtn.setPadding(new Insets(0,7,0,7));
		closeBtn.visibleProperty().bind(tab.selectedProperty());
		closeBtn.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				TabPaneComponent.this.getTabs().remove(tab);
			}
		});
		
		tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean isSelected) {
				if(isSelected){
					tab.setGraphic(closeBtn);
				}else{
					tab.setGraphic(null);
				}
			}
		});
	}
	
	/**
	 * Method used to get the tab by id.
	 * @param id
	 * @return
	 */
	public Tab getTabById(String id){
		for (Tab tab : getTabs()) {
			if(tab.getId().equals( id )){
				return tab;
			}
		}
		return null;
	}

	/**
	 * Selecting the tab in the tab pane with the given tab object.
	 * @param tab
	 */
	public void selectTab(Tab tab){
		super.getSelectionModel().select(tab);
	}
	
	/**
	 * Selecting the tab in the tab pane with the given tab index.
	 * @param index
	 */
	public void selectTab(int index){
		super.getSelectionModel().select(index);
	}
	
	/**
	 * Method used to select the first tab of the tab pane.
	 */
	public void selectFirstTab(){
		super.getSelectionModel().selectFirst();
	}
	
	/**
	 * Method used to deselect all the tabs.
	 */
	public void deselectAllTabs(){		
		super.getSelectionModel().clearSelection();
	}

}
