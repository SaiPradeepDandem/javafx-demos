/**
 * 
 */
package com.ezest.javafx.components.horizontaldragger;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

/**
 * @author amol.hingmire
 * 
 */
public abstract class ControlsDock extends Pane {

	protected double startNodeX =0;
	protected double startDragX;
	protected double xTr;

	public ControlsDock() {
		// TODO Auto-generated constructor stub
	}

	public abstract void addChildren(Control comp);

	public abstract void addEventsOnControl(final Control comp);

	/**
	 * Set up the drag handlers if any control is drag and dropped on
	 * ControlsDock
	 */
	private void setUpDragHandlers() {
		setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent dragEvent) {

				if (dragEvent.getGestureSource() instanceof DragUtility) {
					DragUtility gestureSource = (DragUtility) dragEvent
							.getGestureSource();

				}

			}
		});

		setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() instanceof DragUtility) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

	}

}
