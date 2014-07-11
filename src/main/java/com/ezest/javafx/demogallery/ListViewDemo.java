package com.ezest.javafx.demogallery;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.ezest.javafx.components.TableHeaderListComponent;

public class ListViewDemo extends Application {

	Stage stage;
	Scene scene;
	TilePane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configureHeaderListView();
	}
	private void configureHeaderListView() {
		ObservableList<ListData> list = getListData();
		TableHeaderListComponent<ListData> customListView = new TableHeaderListComponent<ListData>();
		customListView.setHeaderText("Alphabets Sai");
		customListView.getListView().setPrefWidth(300);
		customListView.setItemsInListView(list);
		customListView.getListView().setCellFactory(new Callback<ListView<ListData>, ListCell<ListData>>() {
			@Override
			public ListCell<ListData> call(ListView<ListData> arg0) {
				
				return new ListCell<ListData>(){
					@Override public void updateItem(ListData item, boolean empty) { 
						super.updateItem(item, empty); 
						if (item != null) { 
							HBox hb= TableHeaderListComponent.getTwoColumnSeparatorTemplate(item.getCode(), item.getDesc());
							setGraphic(hb); 
						}
					}
				};
			}
		});
		Callback<ListData, String> onSelectionCallBack = new Callback<ListData, String>() {
			@Override
			public String call(ListData selectedObj) {
				System.out.println("----------------------------> "+selectedObj.getCode());
				return null;
			}
		};
		customListView.setOnSelectionCallBack(onSelectionCallBack);
		root.getChildren().addAll(customListView);
	}
	
	/*private void configureHeaderListView() {
		ObservableList<ListData> list = getListData();
		VBox comp = new VBox();
		comp.setSpacing(10);
		
		Label hlbl = new Label("Alphabets");
		hlbl.setStyle("-fx-font-size:12;");
		
		
		StackPane sp = new StackPane();
		sp.getStyleClass().add("myListColumn");
		sp.setPrefHeight(25);
		sp.setAlignment(Pos.CENTER);
		sp.getChildren().add(hlbl);
		
		Label lbl = new Label("List view with header : ");
		lbl.setStyle("-fx-font-weight:bold;-fx-font-size:17;");
		
		VBox myListComp = new VBox();
		
		ListView<ListData> listView = new ListView<ListData>();
		listView.setItems(list);
		
		listView.setCellFactory(new Callback<ListView<ListData>, ListCell<ListData>>() {
			
			@Override
			public ListCell<ListData> call(ListView<ListData> arg0) {
				
				return new ListCell<ListData>(){
					@Override public void updateItem(ListData item, boolean empty) { 
						//super.
						super.updateItem(item, empty); 
						if (item != null) { 
							HBox hb= new HBox();
							hb.setAlignment(Pos.CENTER_LEFT);
							hb.setSpacing(5);
							Separator sp =  new Separator(Orientation.VERTICAL);
							
							StackPane stkP = new StackPane();
							stkP.setAlignment(Pos.CENTER);
							stkP.setPrefWidth(20);
							stkP.setPadding(new Insets(0, 0, 0, 5));
							stkP.getChildren().add(new Label(item.getCode()));
						
							hb.getChildren().addAll( stkP, sp, new Label(item.getDesc()));
							setGraphic(hb); 
						}
					}
				};
			}
		});
		
		//listView.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<ListData>() { public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) { label.setText(new_val); label.setTextFill(Color.web(new_val)); } });
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ListData>() {
			@Override
			public void changed(
					ObservableValue<? extends ListData> paramObservableValue,	ListData paramT1, ListData paramT2) {
				System.out.println("----------------------------> "+paramT2.getCode());
			}
		});
		myListComp.getChildren().addAll(sp,listView);
		
		
		comp.getChildren().addAll(lbl,myListComp);
		root.getChildren().addAll(comp);
	}*/

	private ObservableList<ListData> getListData() {
		List<ListData> list = new ArrayList<ListData>();
		list.add(new ListData("A", "Apple"));
		list.add(new ListData("B", "Bread"));
		list.add(new ListData("C", "Cool Drink"));
		list.add(new ListData("D", "Drainage"));
		list.add(new ListData("E", "Encyclopedia"));
		list.add(new ListData("F", "Father in law"));
		list.add(new ListData("G", "Goddess"));
		list.add(new ListData("H", "Helicopter"));
		list.add(new ListData("I", "IreLand"));
		list.add(new ListData("J", "Jack n Jill"));
		list.add(new ListData("K", "Kingston"));
		list.add(new ListData("L", "Lantern"));
		list.add(new ListData("M", "Monkey"));
		list.add(new ListData("N", "Nasiruddin Shah"));
		list.add(new ListData("O", "Omani"));
		list.add(new ListData("P", "Pandi"));
		list.add(new ListData("Q", "Queen"));
		list.add(new ListData("R", "Rasberrry"));
		list.add(new ListData("S", "Satthi pandu"));
		list.add(new ListData("T", "Telangana"));
		list.add(new ListData("U", "Uranium"));
		list.add(new ListData("V", "Very good"));
		list.add(new ListData("W", "Wat is that"));
		list.add(new ListData("X", "Xenon rays"));
		list.add(new ListData("Y", "Yatch"));
		list.add(new ListData("Z", "Zindagi na milegi"));
		
		ObservableList<ListData> olist = FXCollections.observableArrayList();
		olist.addAll(list);
		return olist;
	}

	private void configureStage(){
		stage.setTitle("List View Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new TilePane();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/tableListView.css");
	}

}

class ListData{
	private String code;
	private String desc;
	public ListData(String code, String desc) {
		super();
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}


