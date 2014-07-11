package com.ezest.javafx.components.lockingtableview;

import java.util.Iterator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

public class LockingTableView<T> extends StackPane{
	
	private final ObservableList<LockingTableColumn<T,?>> columns;
	private ObjectProperty<ObservableList<T>> items;
	private LockingTableSkin<T> skin ;
	
	@SuppressWarnings("unchecked")
	public LockingTableView(){
		this((ObservableList<T>) FXCollections.observableArrayList());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LockingTableView(ObservableList<T> paramObservableList){
		super();
		skin = new LockingTableSkin<T>(this);
		this.columns = FXCollections.observableArrayList();
		
		this.items = new SimpleObjectProperty(this, "items")
	    {
	      protected void invalidated()
	      {
	    	LockingTableSkin<T> localTableViewSkin = LockingTableView.this.getSkin();
	        localTableViewSkin.updateTableItems(LockingTableView.this.getItems());
	      }
	    };
	    setItems(paramObservableList);
	    
	    getColumns().addListener(new ListChangeListener<LockingTableColumn<T, ?>>() {
			@Override
			public void onChanged(Change<? extends LockingTableColumn<T, ?>> paramChange) {
				while (paramChange.next())
		        {
		          Iterator localIterator = paramChange.getRemoved().iterator();
		          LockingTableColumn localTableColumn;
		          while (localIterator.hasNext())
		          {
		            localTableColumn = (LockingTableColumn)localIterator.next();
		            localTableColumn.setTableView(null);
		          }
		          localIterator = paramChange.getAddedSubList().iterator();
		          while (localIterator.hasNext())
		          {
		            localTableColumn = (LockingTableColumn)localIterator.next();
		            localTableColumn.setTableView(LockingTableView.this);
		          }
		        }
				LockingTableView.this.updateItems();
			}
	    });
	    
	    getChildren().add(skin);
	}
	
	protected void updateItems(){
		getSkin().updateTableItems(getItems());
	}
	
	public LockingTableSkin<T> getSkin(){
		return this.skin;
	}
	
	public ObservableList<LockingTableColumn<T,?>> getColumns(){
		return this.columns;
	}
	
	 public final ObjectProperty<ObservableList<T>> itemsProperty(){
	    return this.items;
	 }
	 public final void setItems(ObservableList<T> paramObservableList){
	    itemsProperty().set(paramObservableList);
	 }

	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 public final ObservableList<T> getItems(){
	    return ((ObservableList)this.items.get());
	 }

}
