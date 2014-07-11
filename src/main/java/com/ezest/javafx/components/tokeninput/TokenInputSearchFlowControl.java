package com.ezest.javafx.components.tokeninput;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.util.Callback;

public class TokenInputSearchFlowControl<ItemType> extends VBox {

	private TextField searchField;
	private Popup popup;
	private ListView<ItemType> availableList;
	private ObservableList<ItemType> chosenItems;
	private ObservableList<Cell> chosenItemCells;
	private NodeFactory<ItemType> nodeFactory;
	private EventHandler<ActionEvent> searchEvent;

	private TokenInputSearchFlowControl<ItemType> content;
	private FlowPane itemsFlowBox;
	
	
	public TokenInputSearchFlowControl() {
		super();
		this.content = this;
		setPadding(new Insets(3));
		setSpacing(3);
		getStyleClass().add("tokenInput");
		itemsFlowBox = new FlowPane();
		itemsFlowBox.setVgap(6);
		itemsFlowBox.setPadding(new Insets(2,0,2,0));
		
		StringBuilder sb = new StringBuilder("-fx-background-color:linear-gradient(from 0px -19px to 0px 0px , repeat, #EEEEEE 76% , #CCC9C1 79% , #FFF0C7 89% );");
		sb.append("-fx-border-color:#CCC9C1;");
		sb.append("-fx-border-width:0px 1.5px 0px 1.5px;");
		sb.append("-fx-border-style: segments(62,14) phase 35;");
		itemsFlowBox.setStyle(sb.toString());
		
		getChildren().add(StackPaneBuilder.create().children(itemsFlowBox).style("-fx-background-color:#FFF0C7").padding(new Insets(0,10,3,10)).build());
		configureSearchField();
	}

	private void configureSearchField() {
		HBox hb = new HBox();
		Image searchImg = new Image(getClass().getResourceAsStream("/images/tokeninput/search.png"));
		ImageView imgView = new ImageView(searchImg);
		imgView.setFitHeight(30);
		imgView.setFitWidth(30);
		imgView.setSmooth(true);

		chosenItems = FXCollections.observableArrayList();
		chosenItemCells = FXCollections.observableArrayList();

		searchField = new TextField();
		searchField.getStyleClass().add("tokenInput-text-field");
		searchField.setPrefHeight(30);
		searchField.setPromptText("Select Search Query..");
		searchField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> source, String oldValue, String newValue) {
				if (newValue != null && newValue.trim().length() > 0 && !popup.isShowing()) {
					showPopup();
				}
			}
		});

		hb.getChildren().addAll(imgView, searchField);
		HBox.setHgrow(searchField, Priority.ALWAYS);
		getChildren().add(hb);

		createPopup();
	}

	public void setCellFactory(Callback<ListView<ItemType>, ListCell<ItemType>> callback) {
		availableList.setCellFactory(callback);
	}

	public ObservableList<ItemType> getAvailableItems() {
		return availableList.getItems();
	}

	public ObservableList<ItemType> getChosenItems() {
		return chosenItems;
	}

	public MultipleSelectionModel<ItemType> getAvailableItemsSelectionModel() {
		return availableList.getSelectionModel();
	}

	public void setNodeFactory(NodeFactory<ItemType> nodeFactory) {
		this.nodeFactory = nodeFactory;
	}

	public Property<String> inputText() {
		return searchField.textProperty();
	}

	private void showPopup() {
		Parent parent = getParent();
		Bounds childBounds = getBoundsInParent();
		Bounds parentBounds = parent.localToScene(parent.getBoundsInLocal());
		double layoutX = childBounds.getMinX() + parentBounds.getMinX() + parent.getScene().getX() + parent.getScene().getWindow().getX();
		double layoutY = childBounds.getMaxY() + parentBounds.getMinY() + parent.getScene().getY() + parent.getScene().getWindow().getY();
		popup.show(this, layoutX, layoutY);
	}

	protected void listItemChosen() {
		ObservableList<ItemType> selectedItems = availableList.getSelectionModel().getSelectedItems();
		if (selectedItems != null && selectedItems.size() > 0 && selectedItems.get(0) != null) {
			ItemType selected = selectedItems.get(0);
			Cell cell = new Cell(selected);
			chosenItemCells.add(cell);
			chosenItems.add(selected);
			// content.getChildren().add(content.getChildren().size() - 1, cell);
			itemsFlowBox.getChildren().add(cell);
			layout();
			popup.hide();
			if (searchEvent != null) {
				searchEvent.handle(null);
			}
		}
	}


	protected void cancelCurrent() {
		popup.hide();
	}

	public EventHandler<ActionEvent> getSearchEvent() {
		return searchEvent;
	}

	public void setSearchEvent(EventHandler<ActionEvent> searchEvent) {
		this.searchEvent = searchEvent;
	}

	protected void createPopup() {
		popup = new Popup();
		availableList = new ListView<ItemType>();
		availableList.getStyleClass().add("drop-shadow");
		availableList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				listItemChosen();
			}
		});

		availableList.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.getCode().equals(KeyCode.ENTER)) {
					listItemChosen();
				} else if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
					cancelCurrent();
				}
			}
		});

		popup.showingProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					searchField.setText("");
				}
			}
		});

		popup.getContent().add(availableList);
		popup.setAutoHide(true);
	}

	private class Cell extends Group {
		private ImageView deleteButton;
		private HBox hb;
		private Cell(final ItemType item) {
			hb = new HBox();
			hb.setAlignment(Pos.CENTER_LEFT);
			hb.setSpacing(6);
			hb.setMinHeight(32);
			hb.setPrefHeight(32);
			StringBuilder builder = new StringBuilder().append("-fx-padding: 2 5 2 5;")
					                                   .append("");
			hb.setStyle(builder.toString());
			getChildren().add(hb);

			Node node;
			if (nodeFactory != null) {
				node = nodeFactory.createNode(item);
			} else {
				node = new Label(String.valueOf(item));
			}
			StackPane sp = new StackPane();
			sp.getChildren().add(node);
			sp.setAlignment(Pos.CENTER_LEFT);

			hb.getChildren().add(sp);
			
			deleteButton = new ImageView(new Image(getClass().getResourceAsStream("/images/tokeninput/cross-script.png")));
			deleteButton.setCursor(Cursor.HAND);
			deleteButton.setStyle("-fx-padding: 0; -fx-text-fill: blue");
			Tooltip.install(deleteButton, new Tooltip("Remove"));
			deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					chosenItemCells.remove(Cell.this);
					chosenItems.remove(item);
					// content.getChildren().remove(Cell.this);
					itemsFlowBox.getChildren().remove(Cell.this);
					layout();
					if (searchEvent != null) {
						searchEvent.handle(null);
					}
				}
			});
			hb.getChildren().add(deleteButton);
			
			ImageView arrow = new ImageView(new Image(getClass().getResourceAsStream("/images/tokeninput/arrow.png")));
			arrow.setFitHeight(32);
			//arrow.setTranslateY(.5);
			hb.getChildren().add(arrow);
		}
		public HBox getNode(){
			return hb;
		}
	}

}

interface NodeFactory<DataType> {
	Node createNode(DataType data);
}