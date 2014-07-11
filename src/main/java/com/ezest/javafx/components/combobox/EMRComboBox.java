package com.ezest.javafx.components.combobox;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.util.Callback;

public class EMRComboBox<ItemType> extends HBox
{
    private ListView<ItemType> popupList;
    private Popup popup;
    private Cell<ItemType> currentItemCell;
    private boolean forceKeepOpen;
    private SimpleDoubleProperty comboWidth = new SimpleDoubleProperty(130);
    private EMRComboBox<ItemType> this$;
    private ItemType value;
    
    public EMRComboBox()
    {
    	this$=this;
        popupList = new ListView<ItemType>();
        popupList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        popupList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemType>()
        {
            public void changed(ObservableValue<? extends ItemType> source, ItemType oldValue, ItemType newValue)
            {
            	setValue(newValue);
                currentItemCell.updateItem(newValue);
                if (!forceKeepOpen)
                {
                    popup.hide();
                }
            }
        });

        addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent event)
            {
                if (KeyCode.UP.equals(event.getCode())
                        || KeyCode.DOWN.equals(event.getCode())
                        || KeyCode.ENTER.equals(event.getCode()))
                {
                    showPopup();
                }
            }
        });

        // Need to force escape key to close since default behaviour is not working
        popupList.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent event)
            {
                if (KeyCode.ESCAPE.equals(event.getCode()))
                {
                    popup.hide();
                }
                else if (KeyCode.UP.equals(event.getCode()))
                {
                    forceKeepOpen = true;
                    popupList.getSelectionModel().select(
                            Math.max(0, popupList.getSelectionModel().getSelectedIndex() - 1));
                    forceKeepOpen = false;
                    event.consume();
                }
                else if (KeyCode.DOWN.equals(event.getCode()))
                {
                    forceKeepOpen = true;
                    popupList.getSelectionModel().select(
                            Math.min(popupList.getItems().size() - 1,
                                    popupList.getSelectionModel().getSelectedIndex() + 1));
                    forceKeepOpen = false;
                    event.consume();
                }
                else if (KeyCode.ENTER.equals(event.getCode()))
                {
                    popup.hide();
                    event.consume();
                }
            }
        });

        /** 
         * FORCE HIDING OF LIST FOR CASE WHEN CURRENT SELECTION IS RE-SELECTED, BUT THIS IS EFFECTING ON DRAG OF SCROLL BAR OF LIST VIEW.
         * CURRENTLY COMMENTING OUT.
         */
        /*popupList.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<Event>()
        {
            public void handle(Event event)
            {
                popup.hide();
            }
        });*/

        popup = new Popup();
        popup.getContent().add(popupList);
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);

        // use your own style for this
        Button popupButton = new Button();
        popupButton.getStyleClass().add("comboButton");
        
        StackPane sp = new StackPane();
        sp.getStyleClass().add("combo-down-arrow");
        sp.setMaxHeight(6);
        popupButton.setGraphic(sp);
        popupButton.setMaxWidth(20);
        popupButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent actionEvent)
            {
                showPopup();
            }
        });
       
        setCurrentItemCell(new SimpleCell<ItemType>());
        super.getChildren().add(popupButton);
       
        /* Settting the focus related properties for the combo box.*/
        ChangeListener<Boolean> focusEvent = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramT1, Boolean focused) {
				if(focused){
					this$.getStyleClass().add("comboFocus");
					this$.getStyleClass().remove("comboFocusOut");
				}else{
					this$.getStyleClass().remove("comboFocus");
				    this$.getStyleClass().add("comboFocusOut");
				}
			}
		};
		popupButton.setFocusTraversable(false);
        popupButton.focusedProperty().addListener(focusEvent);
        ((TextField)currentItemCell).focusedProperty().addListener(focusEvent);
    }

    public ItemType getValue() {
		return value;
	}

	public void setValue(ItemType value) {
		this.value = value;
	}

	public void setComboWidth(double w){
    	this.comboWidth.set(w);
    }
    
    private void setCurrentItemCell(Cell<ItemType> cell)
    {
        currentItemCell = cell;

        ((TextField)currentItemCell).prefWidthProperty().bind(comboWidth);
        
        Node node = currentItemCell.getNode();
        node.setOnMouseClicked(new EventHandler<Event>()
        {
            public void handle(Event event)
            {
                showPopup();
            }
        });

        super.getChildren().add(node);
    }

    public MultipleSelectionModel<ItemType> getSelectionModel()
    {
        return popupList.getSelectionModel();
    }

    public ObservableList<ItemType> getItems()
    {
        return popupList.getItems();
    }

    public void setComboBoxCellFactory(Callback<ListView<ItemType>, ListCell<ItemType>> cellFactory)
    {
        popupList.setCellFactory(cellFactory);
    }

    private void showPopup()
    {
        Parent parent = getParent();
        Bounds childBounds = getBoundsInParent();
        Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());
        double layoutX = childBounds.getMinX()
                + parentBounds.getMinX() + parent.getScene().getX() + parent.getScene().getWindow().getX();
        double layoutY = childBounds.getMaxY()
                + parentBounds.getMinY() + parent.getScene().getY() + parent.getScene().getWindow().getY();
        popup.show(this, layoutX, layoutY);
    }

    
	/**
	 * Cell Inteface
	 * @author Sai.Dandem
	 *
	 * @param <ItemType>
	 */
	public static interface Cell<ItemType>
    {
        Node getNode();

        void updateItem(ItemType item);
    }

	/**
	 * Simple Cell Class
	 * @author Sai.Dandem
	 *
	 * @param <ItemType>
	 */
	public static class SimpleCell<ItemType> extends TextField implements Cell<ItemType>
    {
        public SimpleCell()
        {
            setEditable(false);
            getStyleClass().add("comboTextField");
        }

        public Node getNode()
        {
            return this;
        }

        public void updateItem(ItemType item)
        {
            setText(item != null ? item.toString() : "");
        }
    }
}