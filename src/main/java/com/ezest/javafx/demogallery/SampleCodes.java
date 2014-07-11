package com.ezest.javafx.demogallery;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.ScrollEvent;

public class SampleCodes {

	public void scrollEvent(){
		ListView<String> listView = new ListView<String>();
		listView.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
		  @Override public void handle(ScrollEvent scrollEvent) {
		    System.out.println(scrollEvent);
		  }
		});
	}
	
	
}
