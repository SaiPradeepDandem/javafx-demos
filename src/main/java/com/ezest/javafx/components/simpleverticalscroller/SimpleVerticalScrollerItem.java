package com.ezest.javafx.components.simpleverticalscroller;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class SimpleVerticalScrollerItem extends HBox {

	public SimpleVerticalScrollerItem(Node n) {
		super();
		getChildren().add(n);
	}

}
