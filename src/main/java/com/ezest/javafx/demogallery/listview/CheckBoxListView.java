package com.ezest.javafx.demogallery.listview;

import java.util.List;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Custom {@link ListView} control, which has checkboxes for each object.
 * 
 * @author Sai.Dandem
 * 
 * @param <T>
 */
public class CheckBoxListView<T> extends ListView<T> {
	private SimpleListProperty<T> selectedItems = new SimpleListProperty<T>(FXCollections.<T> observableArrayList());

	public CheckBoxListView() {
		super();
		getStyleClass().add("checkbox-list-view");
		setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
			@Override
			public ListCell<T> call(ListView<T> arg0) {
				ListCell<T> cell = new ListCell<T>() {
					final SimpleObjectProperty<T> data = new SimpleObjectProperty<>();
					private HBox hb = HBoxBuilder.create().spacing(15).build();
					private CheckBox cb = new CheckBox();
					{
						setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent arg0) {
								cb.setSelected(!cb.isSelected());
							}
						});

						cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
							@Override
							public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean selected) {
								if (selected && !selectedItems.contains(data.get())) {
									selectedItems.add(data.get());
								} else if (!selected) {
									selectedItems.remove(data.get());
								}
							}
						});
					}

					@Override
					protected void updateItem(T item, boolean isEmpty) {
						super.updateItem(item, isEmpty);
						if (!isEmpty()) {
							data.set(item);
							hb.getChildren().clear();
							hb.getChildren().addAll(cb, new Text(item.toString()));
							setGraphic(hb);
						} else {
							data.set(null);
							setGraphic(null);
						}
					}
				};
				return cell;
			}
		});
	}
	
	public SimpleListProperty<T> selectedItemsProperty() {
		return selectedItems;
	}
	
	public List<T> getSelectedItems() {
		return selectedItems.get();
	}
	
	public void setSelectedItems(List<T> selectedItems) {
		this.selectedItems.setAll(selectedItems);
	}
}
