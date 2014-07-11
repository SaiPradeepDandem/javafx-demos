/**
 * 
 */
package com.ezest.javafx.components.horizontaldragger;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;

/**
 * @author amol.hingmire
 * 
 */
public class HorizontalDock extends ControlsDock {

	private HBox hBox;
	private boolean isForward;

	public HorizontalDock() {
		hBox = HBoxBuilder.create().spacing(10).alignment(Pos.CENTER_RIGHT).build();
		getChildren().add(hBox);

	}

	@Override
	public void addChildren(Control comp) {
		hBox.getChildren().add(comp);
		addEventsOnControl(comp);
		autosize();
		layoutChildren();
	}

	@Override
	public void addEventsOnControl(final Control comp) {
		EventHandler<MouseEvent> mousePressEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				System.out.println("^^^^^^ IN mouse Press");
				System.out.println(">>>>>>> comp Width = " + comp.getWidth());
				System.out.println("******* event scene x =" + me.getSceneX());
				System.out.println("******* event x =" + me.getX());
				// startNodeX = Math.abs(me.getSceneX()) - Math.abs(me.getX());
				startDragX = me.getSceneX();
				System.out.println("------ startNodeX = " + startNodeX);
				System.out.println("------ startDragX = " + startDragX);
				comp.setCursor(Cursor.MOVE);
				comp.setOpacity(0.4);

			}
		};

		EventHandler<MouseEvent> mouseDraggedEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				System.out.println("^^^^^^ IN mouse Drag");

				if (Double.compare(me.getSceneX(), startDragX) > 0) {
					isForward = true;
				}
				if (Double.compare(me.getSceneX(), startDragX) < 0) {
					isForward = false;
				}

				System.out.println(">>>>>>> isfwd  = " + isForward);
				System.out.println("******* event scene x =" + me.getSceneX());
				System.out.println("******* event x =" + me.getX());
				System.out.println("------ startNodeX = " + startNodeX);
				System.out.println("------ startDragX = " + startDragX);
				double mxDiff = HorizontalDock.this.getWidth() - comp.getWidth();

				double currentXVal = me.getSceneX();

				xTr = startNodeX + (currentXVal - startDragX);

				System.out.println("xtr before calc= " + xTr);
				xTr = xTr < 0 ? 0 : (xTr > mxDiff ? mxDiff : xTr);
				System.out.println("tr x =" + xTr);
				comp.setTranslateX(xTr);
			}
		};

		EventHandler<MouseEvent> mouseReleasedEvent = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				comp.setOpacity(1);
				comp.setCursor(Cursor.DEFAULT);
				setCursor(Cursor.DEFAULT);
				comp.setTranslateX(xTr < 0 ? 0 : xTr);

				int reqControlIndex = hBox.getChildren().indexOf(comp);

				// forward dragging
				if (reqControlIndex != 0 && reqControlIndex != (hBox.getChildren().size() - 1) && isForward) {
					swipeNode(comp, isForward);
				}
				// backward dragging
				if (reqControlIndex != 0 && reqControlIndex != (hBox.getChildren().size() - 1) && !isForward) {
					swipeNode(comp, isForward);
				}

				// For first node and backward dragging
				if (reqControlIndex == 0 && !isForward) {
					comp.setTranslateX(startNodeX);
				}
				// For last node and forward dragging
				if (reqControlIndex == (hBox.getChildren().size() - 1) && isForward) {
					comp.setTranslateX(startNodeX);
				}

			}
		};

		comp.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressEvent);
		comp.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEvent);
		comp.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleasedEvent);

	}

	private void swipeNode(Control srcControl, boolean isForward) {
		int srcControlIndex = hBox.getChildren().indexOf(srcControl);

		Control secondaryNode;
		if (isForward) {
			secondaryNode = (Control) hBox.getChildren().get(srcControlIndex + 1);
		}
		else {
			secondaryNode = (Control) hBox.getChildren().get(srcControlIndex - 1);
		}

		hBox.getChildren().remove(srcControl);
		hBox.getChildren().remove(secondaryNode);

		hBox.getChildren().add(srcControlIndex, secondaryNode);
		if (isForward) {
			hBox.getChildren().add(srcControlIndex + 1, srcControl);
		} else {
			hBox.getChildren().add(srcControlIndex - 1, srcControl);
		}

	}

}
