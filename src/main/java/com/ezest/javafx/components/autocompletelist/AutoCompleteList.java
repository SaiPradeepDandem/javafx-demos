package com.ezest.javafx.components.autocompletelist;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.util.Callback;


public class AutoCompleteList<ItemType> extends BorderPane
{
    private TextField inputField;
    private Popup popup;
    private ListView<ItemType> availableList;
    private ObservableList<ItemType> chosenItems;
    private ObservableList<Cell> chosenItemCells;
    private FlowPane content;
    private NodeFactory<ItemType> nodeFactory;
    private ScrollPane scrollPane;

    public AutoCompleteList()
    {
        buildView();
    }

    public Property<String> inputText()
    {
        return inputField.textProperty();
    }

    public void setCellFactory(Callback<ListView<ItemType>, ListCell<ItemType>> callback)
    {
        availableList.setCellFactory(callback);
    }

    public ObservableList<ItemType> getAvailableItems()
    {
        return availableList.getItems();
    }

    public ObservableList<ItemType> getChosenItems()
    {
        return chosenItems;
    }

    public MultipleSelectionModel<ItemType> getAvailableItemsSelectionModel()
    {
        return availableList.getSelectionModel();
    }

    public void setNodeFactory(NodeFactory<ItemType> nodeFactory)
    {
        this.nodeFactory = nodeFactory;
    }

    private void buildView()
    {
        getStyleClass().add("text-area");

        StringBuilder builder = new StringBuilder()
                .append("-fx-padding: 4;");
        setStyle(builder.toString());

        chosenItems = FXCollections.observableArrayList();
        chosenItemCells = FXCollections.observableArrayList();
        content = new FlowPane(Orientation.HORIZONTAL, 3, 3);

        inputField = new TextField();
//        inputField.getStyleClass().add("auto-complete-list-field");
        inputField.setStyle("-fx-background-insets: 0; -fx-background-color: white");

        inputField.textProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1)
            {
                int cols = s1 != null ? Math.max(2, s1.length() + 1) : 2;
                inputField.setPrefWidth(cols * 8);
            }
        });

        inputField.textProperty().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> source, String oldValue, String newValue)
            {
                if (newValue != null && newValue.trim().length() > 0 && !popup.isShowing())
                {
                    showPopup();
                }
            }
        });
        content.getChildren().add(inputField);

        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(content);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //content.prefWrapLengthProperty().bind(scrollPane.prefWidthProperty().subtract(10));
        setCenter(scrollPane);

        scrollPane.setStyle("-fx-cursor: text");
        scrollPane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                inputField.requestFocus();
            }
        });


        content.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent mouseEvent)
            {
                inputField.requestFocus();
            }
        });

        createPopup();
    }

    protected void showPopup()
    {
        double screenX = inputField.getScene().getWindow().getX() + inputField.getScene().getX() + inputField.localToScene(0, 0).getX();
        double screenY = inputField.getScene().getWindow().getY() + inputField.getScene().getY() + inputField.localToScene(0, 0).getY();
        popup.show(inputField, screenX, screenY + inputField.getHeight());
    }

    protected void listItemChosen()
    {
        ObservableList<ItemType> selectedItems = availableList.getSelectionModel().getSelectedItems();
        if (selectedItems != null && selectedItems.size() > 0 && selectedItems.get(0) != null)
        {
            ItemType selected = selectedItems.get(0);
            Cell cell = new Cell(selected);
            chosenItemCells.add(cell);
            chosenItems.add(selected);
            content.getChildren().add(content.getChildren().size() - 1, cell);
            scrollPane.setVvalue(scrollPane.getVmax());
            layout();
            popup.hide();
        }
    }

    protected void cancelCurrent()
    {
        popup.hide();
    }

    protected void createPopup()
    {
        popup = new Popup();
        availableList = new ListView<ItemType>();
        availableList.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent mouseEvent)
            {
                listItemChosen();
            }
        });

        availableList.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(KeyEvent keyEvent)
            {
                if (keyEvent.getCode().equals(KeyCode.ENTER))
                {
                    listItemChosen();
                }
                else if (keyEvent.getCode().equals(KeyCode.ESCAPE))
                {
                    cancelCurrent();
                }
            }
        });

        popup.showingProperty().addListener(new ChangeListener<Boolean>()
        {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue)
            {
                if (!newValue)
                {
                    inputField.setText("");
                }
            }
        });

        popup.getContent().add(availableList);
        popup.setAutoHide(true);
    }


    //-------------------------------------------------------------------------

    private class Cell extends HBox
    {
        private Hyperlink deleteButton;

        private Cell(final ItemType item)
        {
            setSpacing(6);

            StringBuilder builder = new StringBuilder()
                    .append("-fx-border-color: #cccccc;")
                    .append("-fx-border-radius: 5;")
                    .append("-fx-padding: 2 5 2 5;")
                    .append("-fx-background-radius: 5;")
                    .append("-fx-background-color: #ffffdd;)");
            setStyle(builder.toString());

            Node node;
            if (nodeFactory != null)
            {
                node = nodeFactory.createNode(item);
            }
            else
            {
                node = new Label(String.valueOf(item));
            }
            getChildren().add(node);

            deleteButton = new Hyperlink("X");
            deleteButton.setStyle("-fx-padding: 0; -fx-text-fill: blue");
            deleteButton.setOnAction(new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent event)
                {
                    chosenItemCells.remove(Cell.this);
                    chosenItems.remove(item);
                    content.getChildren().remove(Cell.this);
                    layout();
                }
            });
            getChildren().add(deleteButton);
        }
    }
}
