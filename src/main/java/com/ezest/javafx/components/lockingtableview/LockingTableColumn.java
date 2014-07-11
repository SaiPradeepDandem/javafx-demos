package com.ezest.javafx.components.lockingtableview;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.util.Callback;

public class LockingTableColumn<T, P> {
	private int index ;
	private SimpleBooleanProperty locked = new SimpleBooleanProperty();
	private SimpleObjectProperty<Pos> alignment = new SimpleObjectProperty<Pos>(Pos.CENTER_LEFT);
	private SimpleDoubleProperty width = new SimpleDoubleProperty(100);
	private SimpleStringProperty columnTitle = new SimpleStringProperty();
	private SimpleStringProperty columnProperty = new SimpleStringProperty();
	private SimpleObjectProperty<Node> graphic = new SimpleObjectProperty<Node>();
	private ReadOnlyObjectWrapper<LockingTableView<T>> tableView;
	
	private ObjectProperty<Callback<CellDataFeatures<T, P>, ObservableValue<P>>> cellValueFactory;
	private final ObjectProperty<LockingCallBack<LockingTableColumn<T, P>, LockingTableCell<P>>> cellFactory;
	  
	public final LockingCallBack<T, LockingTableCell<T>> DEFAULT_CELL_FACTORY =
		new LockingCallBack<T, LockingTableCell<T>>(){

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public LockingTableCell<T> call(LockingTableColumn<T, ?> c,	Object paramT) {
				return new LockingTableCell(c, (T)paramT)
			      {
			    	  protected void updateItem(Object item, boolean paramBoolean) {
			    		  if (item == null)
			              {
			                super.setText(null);
			              }
			              else if (item instanceof Node)
			              {
			                super.setGraphic((Node)item);
			              }
			              else
			              {
			            	super.setText(item.toString());
			              }  
			    	  }
				 };
			}
		
	};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LockingTableColumn(){
		this.tableView = new ReadOnlyObjectWrapper(this, "tableView");
		this.cellFactory = new SimpleObjectProperty(this, "cellFactory", DEFAULT_CELL_FACTORY);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LockingTableColumn(String title){
		this.tableView = new ReadOnlyObjectWrapper(this, "tableView");
		setColumnTitle(title);
		this.cellFactory = new SimpleObjectProperty(this, "cellFactory", DEFAULT_CELL_FACTORY);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LockingTableColumn(String title, int index){
		this.tableView = new ReadOnlyObjectWrapper(this, "tableView");
		setColumnTitle(title);
		this.index = index;
		this.cellFactory = new SimpleObjectProperty(this, "cellFactory", DEFAULT_CELL_FACTORY);
	}

	public int getIndex() {
		return index;
	}

	public boolean isLocked() {
		return locked.get();
	}

	public SimpleBooleanProperty lockedProperty() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked.set(locked);
	}

	public Double getWidth() {
		return width.get();
	}

	public SimpleDoubleProperty widthProperty() {
		return width;
	}

	public void setWidth(Double width) {
		this.width.set(width);
	}

	public String getColumnTitle() {
		return columnTitle.get();
	}

	public SimpleStringProperty columnTitleProperty() {
		return columnTitle;
	}

	public void setColumnTitle(String columnTitle) {
		this.columnTitle.set(columnTitle);
	}
	
	public Pos getAlignement() {
		return alignment.get();
	}

	public SimpleObjectProperty<Pos> alignmentProperty() {
		return alignment;
	}

	public void setAlignment(Pos pos) {
		this.alignment.set(pos);
	}
	
	public String getColumnProperty() {
		return columnProperty.get();
	}

	public SimpleStringProperty columnPropertyProperty() {
		return columnProperty;
	}

	public void setColumnProperty(String columnProperty) {
		this.columnProperty.set(columnProperty);
	}
	
	public Node getGraphic() {
		return graphic.get();
	}

	public SimpleObjectProperty<Node> graphicProperty() {
		return graphic;
	}

	public void setGraphic(Node graphic) {
		this.graphic.set(graphic);
	}
	
	public final void setCellValueFactory(Callback<CellDataFeatures<T, P>, ObservableValue<P>> paramCallback){
		cellValueFactoryProperty().set(paramCallback);
	}

	public final ReadOnlyObjectProperty<LockingTableView<T>> tableViewProperty()
	{
		return this.tableView.getReadOnlyProperty();
	}

	final void setTableView(LockingTableView<T> paramTableView)
	{
		this.tableView.set(paramTableView);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final LockingTableView<T> getTableView()
	{
		return ((LockingTableView)this.tableView.get());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final ObservableValue<P> getCellObservableValue(int paramInt)
	  {
	    if (paramInt < 0)
	      return null;
	    LockingTableView localTableView = getTableView();
	    if ((localTableView == null) || (localTableView.getItems() == null))
	      return null;
	    ObservableList<T> localObservableList = localTableView.getItems();
	    if (paramInt >= localObservableList.size())
	      return null;
	    T localObject = localObservableList.get(paramInt);
	    return getCellObservableValue(localObject);
	  }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final ObservableValue<P> getCellObservableValue(T paramS)
	  {
	    Callback localCallback = getCellValueFactory();
	    if (localCallback == null)
	      return null;
	    LockingTableView localTableView = getTableView();
	    if (localTableView == null)
	      return null;
	    CellDataFeatures localCellDataFeatures = new CellDataFeatures(localTableView, this, paramS);
	    return ((ObservableValue)localCallback.call(localCellDataFeatures));
	  }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final ObjectProperty<Callback<CellDataFeatures<T, P>, ObservableValue<P>>> cellValueFactoryProperty(){
		if (this.cellValueFactory == null)
		      this.cellValueFactory = new SimpleObjectProperty(this, "cellValueFactory");
		return this.cellValueFactory;
	}
	 
	public Callback<CellDataFeatures<T, P>, ObservableValue<P>> getCellValueFactory() {
		 return ((this.cellValueFactory == null) ? null : (Callback<CellDataFeatures<T, P>, ObservableValue<P>>)this.cellValueFactory.get());
	}
	
	public LockingCallBack<LockingTableColumn<T, P>, LockingTableCell<P>> getCellFactory() {
		 return ((this.cellFactory == null) ? null : (LockingCallBack<LockingTableColumn<T, P>, LockingTableCell<P>>)this.cellFactory.get());
	}
	
	public final ObjectProperty<LockingCallBack<LockingTableColumn<T, P>, LockingTableCell<P>>> cellFactoryProperty(){
		return this.cellFactory;
	}
	
	public final void setCellFactory(LockingCallBack<LockingTableColumn<T, P>, LockingTableCell<P>> paramCallback){
		cellFactoryProperty().set(paramCallback);
	}

	@SuppressWarnings("hiding")
	public class CellDataFeatures<S, T>
	  {
	    private final LockingTableView<S> tableView;
	    private final LockingTableColumn<S, T> tableColumn;
	    private final S value;

	    public CellDataFeatures(LockingTableView<S> paramTableView, LockingTableColumn<S, T> paramTableColumn, S paramS)
	    {
	      this.tableView = paramTableView;
	      this.tableColumn = paramTableColumn;
	      this.value = paramS;
	    }

	    public S getValue()
	    {
	      return this.value;
	    }

	    public LockingTableColumn<S, T> getTableColumn()
	    {
	      return this.tableColumn;
	    }

	    public LockingTableView<S> getTableView()
	    {
	      return this.tableView;
	    }
	  }
}
