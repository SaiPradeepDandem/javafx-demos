package com.ezest.javafx.components.javafxcombobox;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * Custom Component to add AutoSuggest behavior to the JavaFX ComboBox control.
 * @author Sai.Dandem
 *
 * @param <T>
 */
public class AutoSuggestComboBox<T> extends StackPane{

	private double PREF_HEIGHT = 21.0d;
	private TextField textField;
	private ComboBox<T> comboBox ;
	private ObservableList<T> mainList = FXCollections.observableArrayList();
	private ObservableList<T> subList = FXCollections.observableArrayList();
	private SimpleObjectProperty<T> selectedItem = new SimpleObjectProperty<T>();
	private SimpleBooleanProperty focused = new SimpleBooleanProperty();
	private ChangeListener<String> textChangeLister = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> arg0,
				String arg1, String arg2) {
			boolean flag = false;
			for (T str : mainList) {
				if(str.toString().equals(arg2)){
					flag = true;
					break;
				}
			}
			if(!flag){
				subList.clear();
				for (T str : mainList) {
					if(str.toString().contains(arg2)){
						subList.add(str);
					}
				}
				comboBox.show();
			}
		}
	};
	
	private ChangeListener<T> selectedItemChangeLister = new ChangeListener<T>() {
		@Override
		public void changed(ObservableValue<? extends T> arg0, T arg1, T paramT2) {
			textField.setText(paramT2.toString());
		}
	};
	
	public AutoSuggestComboBox() {
		super();
		setAlignment(Pos.CENTER_LEFT);
		// NOTE: Change the package path accordingly to which the "auto-suggest-combo-box.css" file is added.
		getStylesheets().add("com/ezest/javafx/components/javafxcombobox/auto-suggest-combo-box.css");
		
		selectedItem.addListener(this.selectedItemChangeLister);
		
		// ComboBox declaration
		comboBox = new ComboBox<T>();
		comboBox.setItems(subList);
		comboBox.setPrefHeight(PREF_HEIGHT);
		comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {
			@Override
			public void changed(ObservableValue<? extends T> paramObservableValue,	T paramT1, T paramT2) {
				if(paramT2!=null){
					textField.setText(paramT2.toString());
					selectedItem.set(paramT2);
				}
			}
		});
		comboBox.showingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean show) {
				if(show){
					for (T str : subList) {
						if(str.toString().equals(textField.getText())){
							comboBox.getSelectionModel().select(str);
						}
					}
				}else{
					subList.clear();
					subList.addAll(mainList);
				}
			}
		});
		
		// TextField declaration (for auto suggest)
		textField = new TextField();
		textField.setFocusTraversable(false);
		textField.getStyleClass().add("autoSuggest");
		textField.maxHeightProperty().bind(comboBox.heightProperty());
		textField.maxWidthProperty().bind(comboBox.widthProperty().subtract(18));
		textField.textProperty().addListener(textChangeLister);
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean focused) {
				if(focused){
					comboBox.getStyleClass().add("combo-focused");
				}else{
					comboBox.getStyleClass().remove("combo-focused");
				}
			}
		});
		
		// Adding the children in the stack pane.
		getChildren().addAll(comboBox, textField);
	}
	
	/**
	 * Method to return the ComboBox<T>
	 * @return ComboBox
	 */
	public ComboBox<T> getComboBox(){
		return this.comboBox;
	}
	
	/**
	 * Method to return the selectedItem of the comboBox.
	 * @return SimpleObjectProperty<T>
	 */
	public SimpleObjectProperty<T> selectedItemProperty(){
		return selectedItem;
	}
	
	/**
	 * Method to set the items to the combo box as well as take backup of the list.
	 * NOTE: To get the actual results of AutoSuggest, Setting of items to the combo box should happen through this method only.
	 * @param items ObservableList<T>
	 */
	public void setItemsToCombo(ObservableList<T> items){
		mainList.clear();
		subList.clear();
		mainList.addAll(items);
		subList.addAll(items);
	}
}
