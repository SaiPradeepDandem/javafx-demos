package com.ezest.javafx.demogallery.tableviews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.ProgressIndicatorBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.ezest.javafx.components.lockingtableview.LockPropertyValueFactory;
import com.ezest.javafx.components.lockingtableview.LockingCallBack;
import com.ezest.javafx.components.lockingtableview.LockingTableCell;
import com.ezest.javafx.components.lockingtableview.LockingTableColumn;
import com.ezest.javafx.components.lockingtableview.LockingTableView;
import com.ezest.javafx.domain.LockingTableDTO;

public class LockingTableDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	
	ObservableList<Date> colSelectList = FXCollections.observableArrayList();
	ObservableList<Double> rowSelectList = FXCollections.observableArrayList();
	
	StackPane mask = new StackPane();
	private SimpleBooleanProperty maskVisible = new SimpleBooleanProperty();
	private SimpleStringProperty maskMessage = new SimpleStringProperty();
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configureTable();
		
		
	}

	private void configureMask(){
		
		StackPane maskContainer = new StackPane();
	
		mask.setStyle("-fx-background-color:black;-fx-opacity:.2;");
		mask.visibleProperty().bind(maskVisible);
		
		Label maskLbl = new Label("");
		maskLbl.textProperty().bind(maskMessage);
		maskLbl.setStyle("-fx-font-size:15px;-fx-font-weight:bold;-fx-text-fill:white;");
		
		HBox maskGrp = new HBox();
		maskGrp.setAlignment(Pos.CENTER);
		maskGrp.setSpacing(10);
		maskGrp.getChildren().addAll(ProgressIndicatorBuilder.create().maxWidth(20).maxHeight(20).build()
									,maskLbl);
		mask.getChildren().add(maskGrp);
		showMask("Loading....");
		
		maskContainer.getChildren().addAll(mask,maskGrp);
		
		root.getChildren().add(maskContainer);
		
	}
	
	private void showMask(String msg){
		maskMessage.set(msg);
		maskVisible.set(true);
	}
	
	private void hideMask(){
		maskMessage.set("");
		maskVisible.set(false);
	}
	
	@SuppressWarnings("unchecked")
	private void configureTable() {
		ObservableList<LockingTableDTO> data = TableData.getLockingTableData();
		List<LockingTableColumn<LockingTableDTO,?>> colList = new ArrayList<LockingTableColumn<LockingTableDTO,?>>();
		LockingTableView<LockingTableDTO> table = new LockingTableView<LockingTableDTO>();
		table.setItems(data);
		
		LockingTableColumn<LockingTableDTO,CheckBox> checkColumn = new LockingTableColumn<LockingTableDTO,CheckBox>("");
		checkColumn.setAlignment(Pos.CENTER);
		checkColumn.setWidth(32d);
		checkColumn.setCellValueFactory(new Callback<LockingTableColumn<LockingTableDTO,CheckBox>.CellDataFeatures<LockingTableDTO,CheckBox>, ObservableValue<CheckBox>>() {
			@Override
			public ObservableValue<CheckBox> call(LockingTableColumn<LockingTableDTO,CheckBox>.CellDataFeatures<LockingTableDTO,CheckBox> p) {
				CheckBox cb = new CheckBox();
				cb.setId(p.getValue().getId()+"");
				return new SimpleObjectProperty<CheckBox>(cb);
			}
		});
		checkColumn.setLocked(true);
		checkColumn.setCellFactory(new LockingCallBack<LockingTableColumn<LockingTableDTO,CheckBox>, LockingTableCell<CheckBox>>() {
			@Override
			public LockingTableCell<CheckBox> call(
						LockingTableColumn<LockingTableColumn<LockingTableDTO, CheckBox>, ?> paramColumn,final 	Object paramT) {
				return new LockingTableCell(paramColumn, paramT){
					@Override
					protected void updateItem(Object item, boolean paramBoolean) {
						CheckBox cb = (CheckBox)item;
						final LockingTableDTO obj = (LockingTableDTO)paramT;
						cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
							@Override
							public void changed(
									ObservableValue<? extends Boolean> paramObservableValue,
									Boolean paramT1, Boolean paramT2) {
								if(paramT2){
									rowSelectList.add(obj.getId());
								}else{
									rowSelectList.remove(obj.getId());
								}
								showRowItems();
							}

							private void showRowItems() {
								System.out.println("");
								for (Double rowId : rowSelectList) {
									System.out.print(rowId+", ");
								}
							}
						});
						setGraphic(cb);
					}
				};
			}
		});
		/*checkColumn.setCellFactory(new LockingCallBack<LockingTableDTO, LockingTableCell<LockingTableDTO>>(){

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public LockingTableCell<LockingTableDTO> call(LockingTableColumn<LockingTableDTO, ?> c,	Object paramT) {
				return new LockingTableCell(c, (LockingTableDTO)paramT)
			      {
			    	  protected void updateItem(Object item, boolean paramBoolean) {
			    		  
			    	  }
			      };
			}
		});*/
		
		colList.add(checkColumn);
		
		LockingTableColumn<LockingTableDTO,String> nameColumn = new LockingTableColumn<LockingTableDTO,String>("");
		nameColumn.setCellValueFactory(new LockPropertyValueFactory<LockingTableDTO,String>("name"));
		nameColumn.setWidth(150d);
		nameColumn.setLocked(true);
		colList.add(nameColumn);
		
		LockingTableColumn<LockingTableDTO,String> unitColumn = new LockingTableColumn<LockingTableDTO,String>("Enheid");
		unitColumn.setCellValueFactory(new LockPropertyValueFactory<LockingTableDTO,String>("unit"));
		unitColumn.setWidth(100d);
		unitColumn.setAlignment(Pos.CENTER);
		unitColumn.setLocked(true);
		colList.add(unitColumn);
		
		/* Logic to generate the dynamic columns.*/
		final SimpleIntegerProperty count = new SimpleIntegerProperty();
		for(Date dt : data.get(0).getDateList()){
			final LockingTableColumn<LockingTableDTO,String> valueColumn = new LockingTableColumn<LockingTableDTO,String>("", count.get());
			valueColumn.setCellValueFactory(new Callback<LockingTableColumn<LockingTableDTO,String>.CellDataFeatures<LockingTableDTO,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(LockingTableColumn<LockingTableDTO,String>.CellDataFeatures<LockingTableDTO,String> p) {
					int r = valueColumn.getIndex();
					if (p.getValue().getValueList().get(r) != null)
						return new ReadOnlyStringWrapper(p.getValue().getValueList().get(r)+"");
					else
						return new ReadOnlyStringWrapper("");
				}
			});
			valueColumn.setAlignment(Pos.CENTER);
			valueColumn.setWidth(110d);
			valueColumn.setGraphic( getGraphicNode(dt));
			
			
			colList.add(valueColumn);
			count.set(count.get()+1);
		}
		
		/* Logic to convert list of columns to array of columns.*/
		LockingTableColumn<LockingTableDTO,?> arr[] = new LockingTableColumn[colList.size()];
		for(int k=0;k<colList.size();k++){
			arr[k] = colList.get(k);
		}
		
		// Adding all the columns to the table.
		table.getColumns().addAll(arr);
		
		
		/* Print Button Column */
		Button printBtn = new Button("Print");
		addDisableListeners(printBtn);
		printBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO : Action to print the graph.
			}
		});
		
		VBox.setVgrow(table, Priority.ALWAYS);
		root.getChildren().add(VBoxBuilder.create().children(printBtn,table).spacing(15).build());
	}
	
	private void addDisableListeners(final Button printBtn) {
		ListChangeListener<Object> listener = new ListChangeListener<Object>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Object> paramChange) {
				if(colSelectList.size()>0 && rowSelectList.size()>0){
					printBtn.setDisable(false);
				}else{
					printBtn.setDisable(true);
				}
			}
		};
		colSelectList.addListener(listener);
		rowSelectList.addListener(listener);
	}

	
	public HBox getGraphicNode(final Date dt){
		CheckBox cb = new CheckBox();
		cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean paramT2) {
				if(paramT2){
					colSelectList.add(dt);
				}else{
					colSelectList.remove(dt);
				}
			}
		});
		String str1 = getDatetoString(dt);
		String str2 = getTimetoString(dt);
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(LabelBuilder.create().text(str1).build(),
								LabelBuilder.create().text(str2).build());
		HBox hb = new HBox();
		hb.getChildren().addAll(cb,vb);
		hb.setAlignment(Pos.CENTER);
		hb.setSpacing(5);
		hb.setPadding(new Insets(0,5,0,5));
		return hb;
	}
	
	public String getDatetoString(Date date) {
		 return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	
	public String getTimetoString(Date date) {
		 return new SimpleDateFormat("hh:mm").format(date);
	}
	
	private void configureStage(){
		stage.setTitle("Locking Table Header Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(700);
	    stage.setHeight(400);
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		root.setPadding(new Insets(20));
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/tableviewdemos.css");
	}
}

