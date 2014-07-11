package com.ezest.javafx.components.tokeninput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TokenInputDemo extends Application {

	Stage stage;
	Scene scene;
	VBox root;
	
	private ObservableList<TokenInputData> tableData = FXCollections.observableArrayList();
	private List<TokenInputData> cacheData = new ArrayList<TokenInputData>();
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configureHeader();
		configureSearchControl();
		configureTable();
		
		getMockData();
	}
	
	

	private void configureHeader() {
		StackPane sp = new StackPane();
		ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/images/tokeninput/AIRWebWorldLogo.png")));
		sp.getChildren().add(img);
		sp.setPadding(new Insets(0,0,20,0));
		sp.setAlignment(Pos.CENTER_LEFT);
		root.getChildren().add(sp);
	}

	private void configureSearchControl() {
		final List<String> allItems = Arrays.asList("2005 In Year Of Decision For Long length",
				                                    "2006 In Year Of Decision short length",
				                                    "2007 In Year Of Decision Vary",
				                                    "2008 In Year Of",
				                                    "2009 In Year Of In",
				                                    "2010 In Year Of DFutheru lengh of the gate for me",
				                                    "2011 In Year Of Diamond",
				                                    "2012 In Year Of Gold hill for the",
				                                    "2013 In Yea",
				                                    "2014 In Year Of Decision chdil in the forui",
				                                    "2015 In Year Of Decision");

		final TokenInputSearchFlowControl<String> control = new TokenInputSearchFlowControl<String>();
		
		EventHandler<ActionEvent> searchEvent = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				ObservableList<String> selectedStr = control.getChosenItems();
				Set<Integer> yearList = new HashSet<Integer>();
				for (String str : selectedStr) {
					yearList.add(Integer.parseInt( str.substring(0,4)));
				}
				searchByYear(yearList);
			}
		};
		
		control.setSearchEvent(searchEvent);
		control.inputText().addListener(new ChangeListener<String>()
		        {
		            public void changed(ObservableValue<? extends String> source, String oldValue, String newValue)
		            {
		            	control.getAvailableItems().clear();
		                for (String item : allItems)
		                {
		                    if (item.toLowerCase().contains(newValue))
		                    {
		                    	control.getAvailableItems().add(item);
		                    }
		                }
		                control.getAvailableItemsSelectionModel().selectFirst();
		            }
		        });

		root.getChildren().add(StackPaneBuilder.create().style("-fx-background-color:#85B5D9;-fx-background-radius:5 5 0 0;")
													.styleClass("drop-shadow")
													.padding(new Insets(6))
													.children(control).build());
		
	}

	private void getMockData() {
		int k = 1;
		for (int i = 2005; i < 2011; i++) {
			for(int j = 0 ; j<10;j++){
				cacheData.add(new TokenInputData("Title-"+k+"-"+i, "Description of -"+k+"-"+i, i));
				k++;
			}
		}
	}

	public void searchByYear(Set<Integer> yearList){
		tableData.clear();
		for (Integer year : yearList) {
			for (TokenInputData cache : cacheData) {
				if(cache.getYear().intValue() == year.intValue()){
					tableData.add(cache);
				}
			}
		}
		//System.out.println(tableData.size());
	}
	
	private void configureTable() {
		TableView<TokenInputData> tableView = new TableView<TokenInputData>();
		tableView.setItems(tableData);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn<TokenInputData, String> column1 = new TableColumn<TokenInputData, String>("Title");
		column1.setCellValueFactory(new PropertyValueFactory<TokenInputData, String>("title"));
		
		TableColumn<TokenInputData, String> column2 = new TableColumn<TokenInputData, String>("Description");
		column2.setCellValueFactory(new PropertyValueFactory<TokenInputData, String>("description"));
		
		TableColumn<TokenInputData, Integer> column3 = new TableColumn<TokenInputData, Integer>("Year");
		column3.setCellValueFactory(new PropertyValueFactory<TokenInputData, Integer>("year"));
		
		tableView.getColumns().addAll(column1, column2, column3);
		
		
		root.getChildren().add(StackPaneBuilder.create().style("-fx-background-color:#FFFFFF;-fx-background-radius:0 0 5 5;")
												.padding(new Insets(20,8,8,8))
												.children(tableView).build());
		
	}

	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new VBox();
		root.setPadding(new Insets(20));
		root.autosize();
		root.setMaxWidth(978);
		
		StackPane spbg = new StackPane();
		ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/images/tokeninput/AIRBackground.png")));
		spbg.getChildren().add(img);
		spbg.setAlignment(Pos.TOP_LEFT);
		
		StackPane sp1 = new StackPane();
		sp1.getChildren().addAll(spbg, root);
		
		this.scene = new Scene(sp1, Color.web("#515151"));
		scene.getStylesheets().add("styles/tokenInput.css");
	}

}


