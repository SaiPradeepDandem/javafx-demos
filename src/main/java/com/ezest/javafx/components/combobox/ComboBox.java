package com.ezest.javafx.components.combobox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.util.Callback;

public class ComboBox<ItemType> extends BorderPane
{
    private ListView<ItemType> popupList;
    private Popup popup;
    private Cell<ItemType> currentItemCell;
    private boolean forceKeepOpen;

    public ComboBox()
    {
        popupList = new ListView<ItemType>();
        popupList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        popupList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemType>()
        {
            public void changed(ObservableValue<? extends ItemType> source, ItemType oldValue, ItemType newValue)
            {
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

        // need to force escape key to close since default behaviour is not working
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
                    forceKeepOpen = true;
                    event.consume();
                }
                else if (KeyCode.ENTER.equals(event.getCode()))
                {
                    popup.hide();
                    event.consume();
                }
            }
        });

        // need to force hiding of list for case when current selection is re-selected
        popupList.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<Event>()
        {
            public void handle(Event event)
            {
                popup.hide();
            }
        });

        popup = new Popup();
        popup.getContent().add(popupList);
        popup.setAutoHide(true);
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);

        // use your own style for this
        Button popupButton = new Button(">");
        popupButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent actionEvent)
            {
                showPopup();
            }
        });
        setRight(popupButton);

        setCurrentItemCell(new SimpleCell<ItemType>());
    }

    public void setCurrentItemCell(Cell<ItemType> cell)
    {
        currentItemCell = cell;

        Node node = currentItemCell.getNode();
        node.setOnMouseClicked(new EventHandler<Event>()
        {
            public void handle(Event event)
            {
                showPopup();
            }
        });

        setCenter(node);
    }

    public MultipleSelectionModel<ItemType> getSelectionModel()
    {
        return popupList.getSelectionModel();
    }

    public ObservableList<ItemType> getItems()
    {
        return popupList.getItems();
    }

    public void set(Callback<ListView<ItemType>, ListCell<ItemType>> cellFactory)
    {
        popupList.setCellFactory(cellFactory);
    }

    public void showPopup()
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

    //-------------------------------------------------------------------------

    public static interface Cell<ItemType>
    {
        Node getNode();

        void updateItem(ItemType item);
    }

    public static class SimpleCell<ItemType> extends TextField implements Cell<ItemType>
    {
        public SimpleCell()
        {
            setEditable(false);
            setStyle("-fx-cursor: hand");
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