package com.ezest.javafx.components;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class EditableTableCell<S> extends TableCell<S,String> {
	private final Label label;
	private final Text text;
	private TextField textBox;
	
	public EditableTableCell(EMRTableColumn<S,String> titleCol) {
		super();
		this.label = new Label();
		this.label.setPrefWidth(titleCol.getWidth()-10);
		this.label.setWrapText(true);
		
		this.text = new Text();
		this.text.setWrappingWidth(titleCol.getWidth()-10);
	}
	
	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (!isEmpty()) {
			if (textBox != null) {
				textBox.setText(item);
			}
			label.setText(item);
			text.setText(item);
			setGraphic(text);
		}
	}
	
	@Override
	public void startEdit() {
		super.startEdit();
		if (isEmpty()) {
			return;
		}

		if (textBox == null) {
			createTextBox();
		} else {
			textBox.setText(getItem());
		}
		setGraphic(textBox);
		textBox.requestFocus();
		textBox.selectAll();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setGraphic(text);
	}

	@Override
	public void commitEdit(String t) {
		super.commitEdit(t);
		setGraphic(text);
	}
	
	private void createTextBox() {
		textBox = new TextField(getItem());
		textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent t) {
				if (t.getCode() == KeyCode.ENTER) {
					commitEdit(textBox.getText());
				} else if (t.getCode() == KeyCode.ESCAPE) {
					cancelEdit();
				}
			}
		});
		// textBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
		//
		// @Override
		// public void handle(MouseEvent t) {
		// commitEdit(textBox.getRawText());
		//
		// }
		// });
	}
}
