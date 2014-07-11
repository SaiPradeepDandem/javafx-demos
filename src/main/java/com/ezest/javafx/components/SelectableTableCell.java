package com.ezest.javafx.components;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

public class SelectableTableCell<S> extends TableCell<S,String> {

	private final Label label = new Label();

	public SelectableTableCell() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (t.getClickCount() >= 2) {
					TableRow tableRow = getTableRow();
					System.out.println("hello");
				} 
			}
		});
	}
	public SelectableTableCell(EventHandler<MouseEvent> event) {
		setOnMouseClicked(event);
	}
	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (!isEmpty()) {
			label.setText(item);
			setGraphic(label);
		}
	}

}
