package com.ezest.javafx.components;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * class for creating a collapsible container.
 * 
 * @author <a href="debasmita.sahoo@e-zest.in">Debasmita Sahoo</a>
 * 
 */
public class CollapsibleContainer extends StackPane {
	private Node node;
	private SimpleBooleanProperty expand = new SimpleBooleanProperty();

	public CollapsibleContainer(final Node node, boolean collapsed) {
		super();
		this.node = node;
		this.expand.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean newValue) {
				if (newValue) {
					getChildren().add(node);
				} else {
					getChildren().clear();
				}

			}
		});
		this.expand.set(collapsed);
	}

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * @return the collapsed
	 */
	public boolean getExpand() {
		return expand.get();
	}

	/**
	 * @param expand
	 *            the expand to set
	 */
	public void setExpand(boolean expand) {
		this.expand.set(expand);
	}

	/**
	 * @return the collapsed
	 */
	public SimpleBooleanProperty expandProperty() {
		return expand;
	}

}

