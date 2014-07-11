/**
 * (C) 2014 HealthConnect NV. All rights reserved.
 */
package com.ezest.javafx.components.freetextfield;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Text area control without scrolls. The control height is increased/decreased based on the content.
 * 
 * @author <a href="mailto:sai.dandem@e-zest.in">Sai Dandem</a>
 * 
 */
public class DynamicTextArea extends TextArea {

	private final double rowHeight = 19.33333D;
	private int maxRows = 0;
	private Group group;

	/**
	 * Initializes the {@link DynamicTextArea} control.
	 */
	public DynamicTextArea() {
		super();
		setWrapText(true);
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		if (group == null) {
			final Text text = (Text) lookup(".text");
			final Group group = (Group) text.getParent();
			group.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
				@Override
				public void changed(ObservableValue<? extends Bounds> arg0, Bounds arg1, Bounds bounds) {
					double h = bounds.getHeight() + 11;
					if (getMaxRows() > 0) {
						double ht = h > (getMaxRows() * rowHeight) ? (getMaxRows() * rowHeight) : h;
						setPrefHeight(ht);
						setMaxHeight(ht);
					} else {
						setPrefHeight(h);
						setMaxHeight(h);
					}
				}
			});
			this.group = group;
		}
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

}
