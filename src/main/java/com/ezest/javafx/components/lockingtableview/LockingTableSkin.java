package com.ezest.javafx.components.lockingtableview;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LockingTableSkin<T> extends StackPane{
	private LockingTableView<T> tableView;
	private VBox lockedPane;
	private VBox unlockedPane;
	private HBox paneHolder;
	
	private LockingTableRow lockedHeader;
	private ScrollPane verticalScroll;
	private ScrollPane horizontalScroll;
	private ScrollPane lockedScroll;
	
	private SimpleDoubleProperty lockedWidth = new SimpleDoubleProperty();
	
	public LockingTableSkin(LockingTableView<T> tableView){
		super();
		//System.out.println("Entered in LockingTableSkin -- constructor()");
		this.tableView = tableView;
		
		this.unlockedPane = new VBox();
		this.lockedPane = new VBox();
		this.lockedPane.getStyleClass().add("lockedPane");
		
		/* Locked Scroll */
		final StackPane stk1 = new StackPane(); 
		stk1.setPadding(new Insets(0,0,12,0));
		stk1.getChildren().add(this.lockedPane);
		this.lockedScroll = new ScrollPane(){
			@Override
			protected void layoutChildren() {
				super.layoutChildren();
				setPrefWidth(lockedWidth.get());
				setMaxWidth(lockedWidth.get());
				setMinWidth(lockedWidth.get());
				stk1.setPrefHeight(super.getViewportBounds().getHeight());
			}
		};
		this.lockedScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.lockedScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.lockedScroll.setContent(stk1);
		HBox.setHgrow(this.lockedScroll, Priority.ALWAYS);
		
		/* Horizontal Scroll */
		final StackPane stk2 = new StackPane(); 
		stk2.getChildren().add(this.unlockedPane);
		this.horizontalScroll = new ScrollPane(){
			@Override
			protected void layoutChildren() {
				super.layoutChildren();
				stk2.setPrefHeight(super.getViewportBounds().getHeight());
				/*double viewPortHgt = super.getViewportBounds().getHeight();
				double scrollHgt = super.getHeight();
				System.out.println(viewPortHgt+ " : "+scrollHgt);
				if(scrollHgt==(viewPortHgt+14) || scrollHgt==(viewPortHgt+13)){ // ScrollBar visible
					System.out.println("-----> Scroll Bar Visible");
				}else{ // ScrollBar not visible
					System.out.println("-----> Scroll Bar Not Visible");
				}*/
			}
		};
		this.horizontalScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.horizontalScroll.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		this.horizontalScroll.setContent(stk2);
		HBox.setHgrow(this.horizontalScroll, Priority.ALWAYS);
		
		/* Pane Container */
		this.paneHolder = new HBox();
		this.paneHolder.getChildren().addAll(this.lockedScroll, this.horizontalScroll);
		
		super.getStyleClass().add("lockingTableSkinBG");
		getChildren().add(this.paneHolder);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateTableItems(ObservableList<T> items){
		if(tableView.getColumns()!=null && tableView.getColumns().size()>0 && items!=null && items.size()>0){
			/*Calling method to update the table header row.*/
			updateTableHeaders();
			
			VBox lockedFlow = new VBox();
			VBox unlockedFlow = new VBox();
			int rowCount =0;
			for (T t : items) {
				LockingTableRow lockedRow = new LockingTableRow(rowCount);
				LockingTableRow unlockedRow = new LockingTableRow(rowCount);
				
				lockedRow.minHeightProperty().bind(unlockedRow.heightProperty());
				lockedRow.prefHeightProperty().bind(unlockedRow.heightProperty());
				unlockedRow.prefHeightProperty().bind(lockedRow.heightProperty());
				
				for (LockingTableColumn c : tableView.getColumns()) {
					LockingTableCell<T> cell = (LockingTableCell<T>) c.getCellFactory().call(c, t);
					//LockingTableCell<T> cell = new LockingTableCell<T>(c,t);
					if(c.isLocked()){
						lockedRow.getCells().add(cell);
					}else{
						unlockedRow.getCells().add(cell);
					}
				}
				
				((LockingTableCell<T>)lockedRow.getRow().getChildren().get(lockedRow.getRow().getChildren().size()-1)).makeLastCell();
				
				lockedFlow.getChildren().add(lockedRow);
				unlockedFlow.getChildren().add(unlockedRow);
				rowCount++;
			}
			
			/* Locked Inner Scroll */
			final ScrollPane lockedInnerScroll = new ScrollPane(){
				@Override
				protected void layoutChildren() {
					super.layoutChildren();
					setPrefWidth(lockedWidth.get());
					setMaxWidth(lockedWidth.get());
					setMinWidth(lockedWidth.get());
				}
			};
			lockedInnerScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
			lockedInnerScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
			lockedInnerScroll.getStyleClass().add("vertical-scroll-pane");
			lockedInnerScroll.setContent(lockedFlow);
			VBox.setVgrow(lockedInnerScroll, Priority.ALWAYS);
			this.lockedPane.getChildren().add(lockedInnerScroll);
			
			/* Vertical Scroll */
			this.verticalScroll = new ScrollPane();
			this.verticalScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
			this.verticalScroll.setContent(unlockedFlow);
			this.verticalScroll.getStyleClass().add("vertical-scroll-pane");
			
			/* LOgic to bind the vvalues of the both the scroll panes.*/
				this.verticalScroll.vvalueProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> paramObservableValue,	Number oldValue, Number newValue) {
						lockedInnerScroll.setVvalue(newValue.doubleValue());
					}
				});
				lockedInnerScroll.vvalueProperty().addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> paramObservableValue,	Number oldValue, Number newValue) {
						verticalScroll.setVvalue(newValue.doubleValue());
					}
				});
			/* LOgic end. */
				
			final StackPane stkPane = new StackPane();
			stkPane.getChildren().addAll(this.verticalScroll);
			stkPane.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> paramObservableValue,Number paramT1, Number newValue) {
					/* Required for later development of vertical scroll bar.
					double stackHgt = newValue.doubleValue();
					double contentHgt = ((VBox)verticalScroll.getContent()).getHeight();
					System.out.println("Stk Hgt : "+stackHgt+"\nCnt Hgt : "+contentHgt);
					double diff = contentHgt-stackHgt;
					System.out.println("diff : "+diff);*/
				}
			});
			VBox.setVgrow(stkPane, Priority.ALWAYS);
			this.unlockedPane.getChildren().add(stkPane);
		}
	}
	
	/**
	 * Method to update the table headers row.
	 */
	public void updateTableHeaders(){
		lockedHeader = new LockingTableRow();
		LockingTableRow unlockedHeader = new LockingTableRow();
		for (LockingTableColumn<T, ?> c : tableView.getColumns()) {
			LockingTableCell<T> cell = new LockingTableCell<T>(c);
			if(c.isLocked()){
				lockedHeader.getCells().add(cell);
				lockedWidth.set(lockedWidth.get()+ c.getWidth());
			}else{
				unlockedHeader.getCells().add(cell);
			}
		}
		lockedHeader.minHeightProperty().bind(unlockedHeader.heightProperty());
		
		this.lockedPane.getChildren().clear();
		this.lockedPane.getChildren().add(lockedHeader);
		
		this.unlockedPane.getChildren().clear();
		this.unlockedPane.getChildren().add(unlockedHeader);
	}
}

