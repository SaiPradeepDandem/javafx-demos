/**
 * 
 */
package com.ezest.javafx.components.horizontaldragger;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;

/**
 * @author amol.hingmire
 * 
 */
public class DragUtility extends StackPane {

	private ControlsDock controlsDock;
	private Control control;

	public DragUtility() {
	}

	/**
	 * Sets the Gesture for the control passed.
	 * 
	 * @param control
	 */
	private void setupGestureSource(final Control control) {
		System.out.println("****** setting up the gesture src ");

		control.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				/* drag was detected, start drag-and-drop gesture */
				/* allow any transfer mode */
				Dragboard db = control.startDragAndDrop(TransferMode.ANY);

				/* put a string on dragboard */
				ClipboardContent content = new ClipboardContent();
				db.setContent(content);

				event.consume();
			}
		});

		control.setOnDragDone(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				/* the drag-and-drop gesture ended */
				event.consume();
			}
		});

	}

	/**
	 * Sets the control to drag.
	 * 
	 * @param control
	 * @throws Exception
	 */
	public void setDragControl(Control control) throws Exception {
		if (control == null) {
			throw new Exception("Failed to set Null control...");
		} else {
			this.control = control;
			setupGestureSource(this.control);
		}
	}

	/**
	 * Sets the target Pane.
	 * 
	 * @param controlsDock
	 * @throws Exception
	 */
	public void setTargetPane(ControlsDock controlsDock) throws Exception {
		if (controlsDock == null) {
			throw new Exception("Failed to set Null Target Pane...");
		} else {
			this.controlsDock = controlsDock;
			//this.controlsDock.getChildren().add(this.control);
			//this.controlsDock.addMouseEventsToControls(this.control);
		}

	}

	/**
	 * @return the controlsDock
	 */
	public ControlsDock getControlsDock() {
		return controlsDock;
	}

	/**
	 * @return the control
	 */
	public Control getControl() {
		return control;
	}

}
